function openMenu() {
document.addEventListener('click', () => {
    const menuIcon = document.querySelector('.menu-icon');
    const navLinks = document.querySelector('ul.nav');

    menuIcon.addEventListener('click', function() {
        navLinks.classList.toggle('show');
    });

    checkLoginStatus();
});
}
