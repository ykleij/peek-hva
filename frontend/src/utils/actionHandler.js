import Swal from 'sweetalert2';
import { useToast } from 'vue-toastification';

const toast = useToast();

export const errorHandler = async (error, text) => {
  let message = error?.response?.data?.message
    ? error?.response?.data?.message
    : error;

  toast.error(message || text, {
    timeout: 3000,
    pauseOnHover: false,
  });
};

export const showError = async (message) => {
  toast.error(message, {
    timeout: 3000,
    pauseOnHover: false,
  });
};

export const successHandler = async (message) => {
  // Swal.fire({
  //   icon: 'success',
  //   title: 'Succesfully',
  //   text: message,
  // });

  toast.success(message, {
    timeout: 2000,
    pauseOnHover: false,
  });
};

export const showConfirmation = async (message) => {
  const result = await Swal.fire({
    title: message,
    text: 'You will not be able to revert this!',
    icon: 'warning',
    showCancelButton: true,
    confirmButtonText: 'Yes, confirm it!',
    cancelButtonText: 'No, cancel!',
    reverseButtons: true,
  });

  return result.isConfirmed;
};

export const alertGetInput = async (question) => {
  const result = await Swal.fire({
    title: question,
    input: 'text',
    inputPlaceholder: 'Type your text...',
    showCancelButton: true,
    confirmButtonText: 'send',
    cancelButtonText: 'Cancel',
  });

  if (result.isConfirmed) return result.value;
};
