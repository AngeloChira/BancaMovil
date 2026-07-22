const api = crearApiClient('/api/v1');
let alertas = [];
let alertaSeleccionadaId = null;
let modalInstance = null;

document.addEventListener('DOMContentLoaded', () => {
  // inicializar modal bootstrap
  const modalEl = document.getElementById('modalAlerta');
  modalInstance = new bootstrap.Modal(modalEl);

  // eventos
  document.getElementById('btnNuevo').addEventListener('click', abrirNuevo);
  document.getElementById('btnGenerarSeguridad').addEventListener('click', generarPlantillaSeguridad);
  document.getElementById('formAlerta').addEventListener('submit', guardarAlerta);
  document.getElementById('search').addEventListener('input', renderTabla);
  document.getElementById('btnEnviarSeleccion').addEventListener('click', enviarSeleccionada);
  document.getElementById('btnEnviarBanco').addEventListener('click', enviarABanco);

  // cargar datos iniciales
  cargarAlertas();
  cargarBanco();
  iniciarPollingLogs();
});

async function cargarAlertas() {
  try {
    alertas = await api.listarAlertas();
    renderTabla();
  } catch (e) {
    toast('Error cargando alertas: ' + e.message, 'danger');
  }
}

function renderTabla() {
  const tbody = document.getElementById('tblBody');
  tbody.innerHTML = '';
  const q = document.getElementById('search').value.toLowerCase();
  const filtradas = alertas.filter(a => !q || (a.titulo && a.titulo.toLowerCase().includes(q)) || (a.destinatario && a.destinatario.toLowerCase().includes(q)));
  for (const a of filtradas) {
    const tr = document.createElement('tr');
    tr.innerHTML = `
      <td>${a.id}</td>
      <td>${escapeHtml(a.titulo)}</td>
      <td>${escapeHtml(a.destinatario)}</td>
      <td>${escapeHtml(a.prioridad)}</td>
      <td>${escapeHtml(a.estadoEnvio || '')}</td>
      <td>
        <button class="btn btn-sm btn-outline-primary btn-select" data-id="${a.id}">Seleccionar</button>
        <button class="btn btn-sm btn-outline-success btn-edit" data-id="${a.id}">Editar</button>
        <button class="btn btn-sm btn-outline-danger btn-delete" data-id="${a.id}">Eliminar</button>
      </td>`;
    tbody.appendChild(tr);
  }

  // delegación de eventos
  tbody.querySelectorAll('.btn-select').forEach(b => b.addEventListener('click', e => {
    alertaSeleccionadaId = Number(e.currentTarget.dataset.id);
    toast('Alerta seleccionada: ' + alertaSeleccionadaId, 'info');
  }));
  tbody.querySelectorAll('.btn-edit').forEach(b => b.addEventListener('click', e => editarAlerta(Number(e.currentTarget.dataset.id))));
  tbody.querySelectorAll('.btn-delete').forEach(b => b.addEventListener('click', e => eliminarAlerta(Number(e.currentTarget.dataset.id))));
}

function abrirNuevo() {
  document.getElementById('alId').value = '';
  document.getElementById('alTitulo').value = '';
  document.getElementById('alContenido').value = '';
  document.getElementById('alPrioridad').value = 'Normal';
  document.getElementById('alDestinatario').value = '';
  modalInstance.show();
}

function editarAlerta(id) {
  const a = alertas.find(x => x.id === id);
  if (!a) return toast('Alerta no encontrada', 'warning');
  document.getElementById('alId').value = a.id;
  document.getElementById('alTitulo').value = a.titulo;
  document.getElementById('alContenido').value = a.contenido;
  document.getElementById('alPrioridad').value = a.prioridad || 'Normal';
  document.getElementById('alDestinatario').value = a.destinatario;
  modalInstance.show();
}

async function guardarAlerta(evt) {
  evt.preventDefault();
  const id = document.getElementById('alId').value;
  const dto = {
    titulo: document.getElementById('alTitulo').value,
    contenido: document.getElementById('alContenido').value,
    prioridad: document.getElementById('alPrioridad').value,
    destinatario: document.getElementById('alDestinatario').value
  };
  try {
    if (!id) {
      const creado = await api.crearAlerta(dto);
      alertas.push(creado);
      toast('Alerta creada', 'success');
    } else {
      // backend in-memory no tiene PUT en el esqueleto; para demo recreamos: eliminar y crear con mismo id no necesario
      // Si implementas PUT, aquí llama a actualizar.
      toast('Edición no implementada en demo in-memory', 'info');
    }
    modalInstance.hide();
    renderTabla();
  } catch (e) {
    toast('Error guardando: ' + e.message, 'danger');
  }
}

async function eliminarAlerta(id) {
  if (!confirm('Eliminar alerta ' + id + '?')) return;
  try {
    await api.eliminarAlerta(id);
    alertas = alertas.filter(a => a.id !== id);
    renderTabla();
    toast('Alerta eliminada', 'success');
  } catch (e) {
    toast('Error eliminando: ' + e.message, 'danger');
  }
}

function generarPlantillaSeguridad() {
  // usa el builder en backend si implementas usarPlantillaSeguridad=true
  document.getElementById('alTitulo').value = 'Codigo de Seguridad';
  document.getElementById('alContenido').value = 'Su codigo es 123456';
  document.getElementById('alPrioridad').value = 'Alta';
  document.getElementById('alDestinatario').value = 'Cliente';
  modalInstance.show();
}

async function enviarSeleccionada() {
  if (!alertaSeleccionadaId) return toast('Selecciona una alerta primero', 'warning');
  const payload = construirPayload();
  payload.estadoSistema = document.getElementById('selectEstado').value;
  try {
    const res = await api.enviarAlerta(alertaSeleccionadaId, payload);
    toast('Envío: ' + JSON.stringify(res), 'success');
    // actualizar estado local
    const a = alertas.find(x => x.id === alertaSeleccionadaId);
    if (a) a.estadoEnvio = 'SENT';
    renderTabla();
  } catch (e) {
    toast('Error enviando: ' + e.message, 'danger');
  }
}

async function enviarABanco() {
  if (!alertaSeleccionadaId) return toast('Selecciona una alerta primero', 'warning');
  // En la demo, enviar a banco simplemente reusa el endpoint enviar con target banco
  const payload = construirPayload();
  payload.target = 'banco';
  payload.estadoSistema = document.getElementById('selectEstado').value;
  try {
    const res = await api.enviarAlerta(alertaSeleccionadaId, payload);
    toast('Envío a banco: ' + JSON.stringify(res), 'success');
  } catch (e) {
    toast('Error enviando a banco: ' + e.message, 'danger');
  }
}

function construirPayload() {
  const canal = document.querySelector('input[name="canal"]:checked').value;
  const decorators = [];
  if (document.getElementById('chkUrgente').checked) decorators.push('urgente');
  if (document.getElementById('chkPrioritaria').checked) decorators.push('prioritaria');
  if (document.getElementById('chkCifrado').checked) decorators.push('cifrado');
  const estrategia = document.getElementById('selectEstrategia').value;
  return { canal, decorators, estrategia };
}

async function cargarBanco() {
  try {
    const data = await api.obtenerBanco();
    renderBanco(data);
  } catch (e) {
    document.getElementById('bankTree').innerText = 'No se pudo cargar estructura del banco';
  }
}

function renderBanco(data) {
  const container = document.getElementById('bankTree');
  container.innerHTML = '';
  if (!data || !data.agencias) { container.innerText = 'Sin datos'; return; }
  const ul = document.createElement('ul');
  ul.className = 'list-unstyled';
  data.agencias.forEach(ag => {
    const li = document.createElement('li');
    const h = document.createElement('div');
    h.innerHTML = `<strong>${escapeHtml(ag.nombre)}</strong>`;
    li.appendChild(h);
    const cajUl = document.createElement('ul');
    cajUl.className = 'ms-3';
    (ag.cajeros || []).forEach(c => {
      const cLi = document.createElement('li');
      cLi.textContent = c;
      cajUl.appendChild(cLi);
    });
    li.appendChild(cajUl);
    ul.appendChild(li);
  });
  container.appendChild(ul);
}

function iniciarPollingLogs() {
  const logsEl = document.getElementById('logs');
  async function poll() {
    try {
      const logs = await api.obtenerLogs();
      logsEl.innerHTML = logs.map(l => `<div>${escapeHtml(l)}</div>`).join('');
      logsEl.scrollTop = logsEl.scrollHeight;
    } catch (e) {
      logsEl.innerText = 'Error cargando logs';
    } finally {
      setTimeout(poll, 2500);
    }
  }
  poll();
}

// util
function escapeHtml(s) {
  if (!s) return '';
  return s.replaceAll('&','&amp;').replaceAll('<','&lt;').replaceAll('>','&gt;');
}
