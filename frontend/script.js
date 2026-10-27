const schemes = [
  { id: 1, name: "National Scholarship Portal", category: "Education", icon: "🎓", tone: "blue", summary: "A central place to explore scholarships offered by participating government departments.", audience: "Students", benefit: "Scholarships vary by programme", age: [1, 35], income: 500000, occupations: ["student"], official: "https://scholarships.gov.in/" },
  { id: 2, name: "PM-KISAN", category: "Agriculture", icon: "🌱", tone: "green", summary: "Income-support programme for eligible landholding farmer families, subject to scheme rules.", audience: "Eligible farmer families", benefit: "₹6,000 per year (subject to rules)", age: [18, 100], income: 1000000, occupations: ["farmer"], official: "https://pmkisan.gov.in/" },
  { id: 3, name: "Pradhan Mantri MUDRA Yojana", category: "Business", icon: "💼", tone: "orange", summary: "Explore loans through eligible lenders for qualifying small and micro business activities.", audience: "Small business owners", benefit: "Loan terms depend on lender and category", age: [18, 100], income: 1000000, occupations: ["entrepreneur"], official: "https://www.mudra.org.in/" },
  { id: 4, name: "Pradhan Mantri Awas Yojana", category: "Housing", icon: "⌂", tone: "yellow", summary: "Housing support programmes for eligible households; components and criteria can vary.", audience: "Eligible households", benefit: "Support depends on programme component", age: [18, 100], income: 500000, occupations: ["student", "farmer", "entrepreneur", "woman", "jobseeker", "other"], official: "https://pmay-urban.gov.in/" },
  { id: 5, name: "Sukanya Samriddhi Account", category: "Women & Child", icon: "♡", tone: "pink", summary: "A savings scheme for a girl child, opened by an eligible parent or guardian under account rules.", audience: "Girl child and guardian", benefit: "Government-notified interest rate", age: [0, 10], income: 10000000, occupations: ["woman", "other"], official: "https://www.indiapost.gov.in/" },
  { id: 6, name: "Stand-Up India", category: "Business", icon: "↗", tone: "orange", summary: "Explore bank loans for eligible women and SC/ST entrepreneurs setting up greenfield enterprises.", audience: "Eligible entrepreneurs", benefit: "Loan support subject to eligibility", age: [18, 100], income: 10000000, occupations: ["entrepreneur", "woman"], official: "https://www.standupmitra.in/" },
  { id: 7, name: "Ayushman Bharat PM-JAY", category: "Healthcare", icon: "✚", tone: "green", summary: "Check official eligibility for publicly funded hospital care under the programme.", audience: "Eligible families", benefit: "Coverage subject to programme rules", age: [1, 100], income: 500000, occupations: ["student", "farmer", "entrepreneur", "woman", "jobseeker", "other"], official: "https://pmjay.gov.in/" },
  { id: 8, name: "National Career Service", category: "Employment", icon: "⌕", tone: "purple", summary: "Explore job-search, career guidance and employment-related services on the official portal.", audience: "Job seekers", benefit: "Career and employment services", age: [18, 100], income: 10000000, occupations: ["jobseeker", "student", "other"], official: "https://www.ncs.gov.in/" },
  { id: 9, name: "PM Vishwakarma", category: "Business", icon: "⚒", tone: "orange", summary: "Support for eligible traditional artisans and craftspeople under scheme-specific conditions.", audience: "Eligible artisans", benefit: "Training and other support subject to rules", age: [18, 100], income: 10000000, occupations: ["entrepreneur", "other"], official: "https://pmvishwakarma.gov.in/" }
];

const schemeGrid = document.getElementById("schemeGrid");
const searchInput = document.getElementById("schemeSearch");
const categoryButtons = [...document.querySelectorAll(".category-card")];
const modal = document.getElementById("eligibilityModal");
const form = document.getElementById("eligibilityForm");
const results = document.getElementById("matchResults");
let selectedCategory = "All";

function renderSchemes() {
  const query = searchInput.value.trim().toLowerCase();
  const filtered = schemes.filter(s =>
    (selectedCategory === "All" || s.category === selectedCategory) &&
    `${s.name} ${s.category} ${s.summary} ${s.audience}`.toLowerCase().includes(query)
  );
  schemeGrid.innerHTML = "";
  if (!filtered.length) {
    schemeGrid.innerHTML = '<div class="no-results">No matching guides found. Try another search term or choose “All schemes”.</div>';
    return;
  }
  filtered.slice(0, 6).forEach(s => {
    const card = document.createElement("article");
    card.className = "scheme-card";
    card.innerHTML = `
      <div class="scheme-top"><span class="scheme-icon ${s.tone}">${s.icon}</span><span class="scheme-tag">${s.category}</span></div>
      <h3>${s.name}</h3><p>${s.summary}</p>
      <div class="scheme-meta"><div><small>WHO IT MAY HELP</small><strong>${s.audience}</strong></div>
      <button class="details-btn" data-id="${s.id}">Official details ↗</button></div>`;
    schemeGrid.appendChild(card);
  });
}
function showToast(message) {
  const toast = document.getElementById("toast");
  toast.textContent = message;
  toast.classList.add("show");
  clearTimeout(showToast.timer);
  showToast.timer = setTimeout(() => toast.classList.remove("show"), 2600);
}
function openModal() {
  modal.classList.add("open");
  modal.setAttribute("aria-hidden", "false");
  document.body.style.overflow = "hidden";
  setTimeout(() => document.getElementById("age").focus(), 100);
}
function closeModal() {
  modal.classList.remove("open");
  modal.setAttribute("aria-hidden", "true");
  document.body.style.overflow = "";
}
document.getElementById("heroCheck").addEventListener("click", openModal);
document.getElementById("navCheck").addEventListener("click", openModal);
document.getElementById("bottomCheck").addEventListener("click", openModal);
document.getElementById("modalClose").addEventListener("click", closeModal);
modal.addEventListener("click", e => { if (e.target === modal) closeModal(); });
document.addEventListener("keydown", e => { if (e.key === "Escape" && modal.classList.contains("open")) closeModal(); });

document.getElementById("menuToggle").addEventListener("click", () => {
  document.getElementById("mainNav").classList.toggle("open");
});
document.querySelectorAll("#mainNav a").forEach(link => link.addEventListener("click", () => {
  document.getElementById("mainNav").classList.remove("open");
}));
categoryButtons.forEach(button => button.addEventListener("click", () => {
  selectedCategory = button.dataset.category;
  categoryButtons.forEach(b => b.classList.toggle("selected", b === button));
  renderSchemes();
  document.getElementById("scheme-list").scrollIntoView({ behavior: "smooth", block: "start" });
}));
searchInput.addEventListener("input", renderSchemes);
schemeGrid.addEventListener("click", e => {
  const button = e.target.closest("[data-id]");
  if (!button) return;
  const scheme = schemes.find(s => s.id === Number(button.dataset.id));
  if (scheme) window.open(scheme.official, "_blank", "noopener,noreferrer");
});

form.addEventListener("submit", e => {
  e.preventDefault();
  const age = Number(document.getElementById("age").value);
  const income = Number(document.getElementById("income").value);
  const occupation = document.getElementById("occupation").value;
  const need = document.getElementById("need").value;
  const state = document.getElementById("state").value;
  const matches = schemes.map(s => {
    let score = 0;
    if (age >= s.age[0] && age <= s.age[1]) score += 35;
    if (income <= s.income) score += 25;
    if (s.occupations.includes(occupation)) score += 30;
    if (need === "All" || need === s.category) score += 10;
    return { ...s, score };
  }).filter(s => s.score >= 45 && (need === "All" || s.category === need))
    .sort((a, b) => b.score - a.score).slice(0, 4);

  results.hidden = false;
  if (!matches.length) {
    results.innerHTML = `<h3>Let's widen the search.</h3><p>We couldn't find a close match in this small demo catalogue for your selected filters. Try “Any relevant support” or verify options on the official portals.</p>`;
  } else {
    results.innerHTML = `<h3>Your suggested scheme guides</h3><p>Based on the answers you entered for ${escapeHtml(state)}. Scores are demo relevance scores, not official eligibility decisions.</p>` +
      matches.map(s => `<div class="result-item"><div><strong>${escapeHtml(s.name)}</strong><small>${escapeHtml(s.category)} · ${escapeHtml(s.benefit)}</small></div><span class="result-score">${s.score}% match</span></div>`).join("") +
      `<p style="margin-top:14px">Please open the official scheme website and confirm all current criteria before applying.</p>`;
  }
  results.scrollIntoView({ behavior: "smooth", block: "nearest" });
});
function escapeHtml(value) {
  return String(value).replace(/[&<>"']/g, char => ({ "&":"&amp;", "<":"&lt;", ">":"&gt;", '"':"&quot;", "'":"&#39;" }[char]));
}
renderSchemes();
