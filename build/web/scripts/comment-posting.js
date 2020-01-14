/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
const commentPosting = document.getElementById('comment-posting')
const postingBtn = document.getElementById('posting-btn')

commentPosting.addEventListener('keyup', () => {
    let commentContent = commentPosting.value.trim()
    if (commentContent == '') {
        postingBtn.disabled = true
        postingBtn.style.background = 'rgba(19, 22, 39, 0.8)'
    } else {
        postingBtn.disabled = false
        postingBtn.style.background = '#131627'
    }
})


