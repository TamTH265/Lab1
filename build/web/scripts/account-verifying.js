/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
const verifyingCode = document.getElementById('verifying-code')
const verifyingBtn = document.getElementById('verifying-btn')
const verifyingCodeError = document.getElementById('verifying-code-error')

verifyingBtn.addEventListener('click', (e) => {
    let isValid
    const verifyingCodeRegx = /[0-9]{6}/

    if (!verifyingCodeRegx.test(verifyingCode)) {
        verifyingCodeError.innerHTML = 'Verifying Code is INVALID'
        verifyingCode.innerHTML = ''
    } else {
        verifyingCodeError = ''
    }

    if (verifyingCodeError.innerHTML == '') {
        isValid == true
    } else {
        isValid = false
    }

    if (!isValid) {
        e.preventDefault()
    }
})


