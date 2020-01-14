/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
const searchContent = document.getElementById('search-content')
const searchBtn = document.getElementById('search-btn')
const searchError = document.getElementById('search-error')

searchBtn.addEventListener('click', (e) => {
    let isValid
    if (searchContent.value.trim() == '') {
        searchError.innerHTML = 'Please input value to search!'
        isValid = false
    } else {
        isValid = true
    }
    
    if (!isValid) {
        e.preventDefault()
    }
})


