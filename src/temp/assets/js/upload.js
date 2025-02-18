const fileInput = document.getElementById('fileInput');
const fileTableBody = document.getElementById('fileTableBody');
const uploadButton = document.getElementById('uploadButton');

let filesToUpload = [];

fileInput.addEventListener('change', function () {
    const files = Array.from(fileInput.files);

    files.forEach(file => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
                    <td><input type="checkbox" class="file-checkbox"></td>
                    <td>${file.name}</td>
                    <td>${(file.size / (1024 * 1024)).toFixed(2)} MB</td>
                    <td class="upload-status not-uploaded">Not uploaded</td>
                `;
        fileTableBody.appendChild(tr);

        filesToUpload.push(file);
    });
});

uploadButton.addEventListener('click', function () {
    const selectedFiles = [];

    document.querySelectorAll('.file-checkbox').forEach((checkbox, index) => {
        if (checkbox.checked) {
            selectedFiles.push(filesToUpload[index]);
        }
    });

    if (selectedFiles.length > 0) {
        uploadFiles(selectedFiles);
    } else {
        alert('Please select at least one file to upload.');
    }
});

function uploadFiles(files) {
    const formData = new FormData();

    files.forEach((file, index) => {
        formData.append('file' + index, file);
        formData.append('fileName' + index, file.name);
        formData.append('fileSize' + index, (file.size / (1024 * 1024)).toFixed(2)); // Taille en MB
    });

    const xhr = new XMLHttpRequest();
    xhr.open('POST', 'upload.FileController', true);

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {

            document.querySelectorAll('.file-checkbox').forEach((checkbox, index) => {
                if (checkbox.checked) {
                    const row = checkbox.parentElement.parentElement;
                    const statusCell = row.querySelector('.upload-status');
                    statusCell.textContent = 'Uploaded';
                    statusCell.classList.remove('not-uploaded');
                    statusCell.classList.add('uploaded');
                }
            });
            // alert(xhr.responseText);
            alert('Files uploaded successfully!');
        }
    };

    xhr.send(formData);
}