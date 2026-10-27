const API_BASE = localStorage.getItem("schemeApiBase") || "http://localhost:8080/api";
const SchemeAPI = {
  async request(path, options = {}) {
    const token = localStorage.getItem("schemeToken");
    const headers = { "Content-Type": "application/json", ...(options.headers || {}) };
    if (token) headers.Authorization = `Bearer ${token}`;
    const response = await fetch(`${API_BASE}${path}`, { ...options, headers });
    const body = response.status === 204 ? null : await response.json().catch(() => null);
    if (!response.ok) throw new Error(body?.message || body?.error || `Request failed (${response.status})`);
    return body;
  },
  schemes: () => SchemeAPI.request("/schemes"),
  recommend: profile => SchemeAPI.request("/recommendations", { method: "POST", body: JSON.stringify(profile) }),
  register: data => SchemeAPI.request("/auth/register", { method: "POST", body: JSON.stringify(data) }),
  login: data => SchemeAPI.request("/auth/login", { method: "POST", body: JSON.stringify(data) }),
  saved: () => SchemeAPI.request("/saved"),
  save: schemeId => SchemeAPI.request(`/saved/${schemeId}`, { method: "POST" }),
  unsave: schemeId => SchemeAPI.request(`/saved/${schemeId}`, { method: "DELETE" }),
  adminCreateScheme: data => SchemeAPI.request("/admin/schemes", { method: "POST", body: JSON.stringify(data) }),
  adminDeleteScheme: id => SchemeAPI.request(`/admin/schemes/${id}`, { method: "DELETE" })
};
