function toggleMenu(event) {
    // Empêche la propagation de l'événement pour éviter d'autres déclenchements
    event.stopPropagation();

    // Récupère le menu associé à l'élément cliqué
    const menu = event.currentTarget.parentElement.nextElementSibling;

    // Vérifie si le menu est visible
    const isVisible = menu.style.display === 'block';

    // Masque tous les autres menus
    const allMenus = document.querySelectorAll('.menu');
    allMenus.forEach(m => m.style.display = 'none');

    // Si le menu n'est pas visible, l'affiche
    menu.style.display = isVisible ? 'none' : 'block';
}

// Ferme tous les menus si l'utilisateur clique en dehors
document.addEventListener('click', () => {
    const allMenus = document.querySelectorAll('.menu');
    allMenus.forEach(m => m.style.display = 'none');
});
