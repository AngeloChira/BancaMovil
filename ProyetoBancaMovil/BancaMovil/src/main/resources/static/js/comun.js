// crearApiClient(baseUrl) -> cliente simple para alertas, logs y banco
function crearApiClient(baseUrl) {
  async function request(path, method='GET', body=null) {
    const opts = { method, headers: { 'Content-Type': 'application/json' } };
    if (body) opts.body = JSON.stringify(body);
    const res = await fetch(baseUrl + path, opts);
    if (!res.ok) {
      const txt = await res.text();
      throw new Error(`HTTP ${res.status} - ${txt || res.statusText}`);
    }
    return res.status === 204 ? null : res.json();
  }

  return {
    listarAlertas: () => request('/alertas'),
    crearAlerta: (data) => request('/alertas', 'POST', data),
    eliminarAlerta: (id) => request(`/alertas/${id}`, 'DELETE'),
    enviarAlerta: (id, payload) => request(`/alertas/${id}/enviar`, 'POST', payload),
    obtenerLogs: () => fetch('/api/v1/logs').then(r => r.ok ? r.json() : []),
    obtenerBanco: () => fetch('/api/v1/banco').then(r => r.ok ? r.json() : {}),
  };
}

// helper: mostrar notificacion simple
function toast(msg, tipo='info') {
  console.log(`[${tipo}] ${msg}`);
}
