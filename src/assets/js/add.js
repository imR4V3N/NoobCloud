const addBtn = document.querySelector('.add-btn');
const section = document.querySelector('.add-section');
const cancelBtn = document.querySelector('#cancel');

addBtn.addEventListener('click', () => {
    section.style.display = 'grid';
});

cancelBtn.addEventListener('click', () => {
    section.style.display = 'none';
});