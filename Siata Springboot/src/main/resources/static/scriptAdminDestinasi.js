document.getElementById('destinationForm').addEventListener('submit', async function(event) {
    event.preventDefault();
    
    const formData = new FormData(this);
    const photoFile = formData.get('photo');
    const photoBase64 = await toBase64(photoFile);

    const destinationData = {
        destinationName: formData.get('destinationName'),
        description: formData.get('description'),
        location: formData.get('location'),
        photo: photoBase64
    };

    fetch('/api/destination', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${getCookie('accessToken')}`
        },
        body: JSON.stringify(destinationData)
    })
    .then(response => response.json())
    .then(data => {
        console.log('Success:', data);
        // Optionally, update the destination list or give feedback to the user
    })
    .catch((error) => {
        console.error('Error:', error);
    });
});

function toBase64(file) {
    return new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = () => resolve(reader.result);
        reader.onerror = error => reject(error);
    });
}
