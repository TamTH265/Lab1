/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
const postingBtn = document.getElementById('posting-btn')
const title = document.getElementById('title')
const shortDescription = document.getElementById('short-description')
const content = document.getElementById('content')
const titleError = document.getElementById('title-error')
const shortDescriptionError = document.getElementById('short-description-error')
const contentError = document.getElementById('content-error')

postingBtn.addEventListener('click', (e) => {
    let isValid
    if (title.value == '') {
        titleError.innerHTML = 'Title cannot be blank!'
    } else {
        titleError.innerHTML = ''
    }

    if (shortDescription.value == '') {
        shortDescriptionError.innerHTML = 'Short Description cannot be blank!'
    } else {
        shortDescriptionError.innerHTML = ''
    }

    if (content.value == '') {
        contentError.innerHTML = 'Content cannot be blank!'
    } else {
        contentError.innerHTML = ''
    }

    if (titleError.innerHTML == '' && shortDescriptionError.innerHTML == '' && contentError.innerHTML == '') {
        isValid = true
    } else {
        isValid = false
    }

    if (!isValid) {
        e.preventDefault()
    }
})
