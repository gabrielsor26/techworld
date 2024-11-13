

const $navBarUser = document.querySelector('.navBarUser')

document.addEventListener('click',(e)=>{
    if (e.target.closest('.btnUser')){
        $navBarUser.classList.toggle('hidden')
    }
});