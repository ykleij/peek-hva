export function getImageBLOBUrl(user) {
    if (user && user.imageData) {
        const decodedImageData = atob(user.imageData);
        const arrayBuffer = new Uint8Array(decodedImageData.length);
        for (let i = 0; i < decodedImageData.length; i++) {
            arrayBuffer[i] = decodedImageData.charCodeAt(i);
        }
        const blob = new Blob([arrayBuffer], {type: 'image/*'});
        return URL.createObjectURL(blob);
    }

    return require('@/assets/img/defaultpfp.jpg');
}

export function getImageBLOBUrlProject(project) {
    if (project && project.imageData) {
        const decodedImageData = atob(project.imageData);
        const arrayBuffer = new Uint8Array(decodedImageData.length);
        for (let i = 0; i < decodedImageData.length; i++) {
            arrayBuffer[i] = decodedImageData.charCodeAt(i);
        }
        const blob = new Blob([arrayBuffer], {type: 'image/*'});
        return URL.createObjectURL(blob);
    }

    return require('@/assets/img/default-project-image.jpg');
}

