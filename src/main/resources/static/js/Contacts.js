/*console.log("contacts.js");

const viewContactModal = document.getElementById('view_contact_modal');

const contactModal = new Modal(viewContactModal, options);

function openContactModal() {
    contactModal.show();
}

// options with default values
const options = {
    placement: 'bottom-right',
    backdrop: 'dynamic',
    backdropClasses:
        'bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40',
    closable: true,
    onHide: () => {
        console.log('modal is hidden');
    },
    onShow: () => {
        console.log('modal is shown');
    },
    onToggle: () => {
        console.log('modal has been toggled');
    },
};

// Create a new Modal instance with options


// Example: Add event listener to a button that opens the modal
document.getElementById('openModalButton').addEventListener('click', openContactModal);*/

console.log("contacts.js");
const baseURL = "http://localhost:8081";

// Ensure DOM is fully loaded before running the script
document.addEventListener('DOMContentLoaded', () => {
    const viewContactModal = document.getElementById('view_contact_modal');

    // options with default values
    const options = {
        placement: 'bottom-right',
        backdrop: 'dynamic',
        backdropClasses: 'bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40',
        closable: true,
        onHide: () => {
            console.log('modal is hidden');
        },
        onShow: () => {
            console.log('modal is shown');
        },
        onToggle: () => {
            console.log('modal has been toggled');
        },
    };

    // instance options object
    const instanceOptions = {
        id: 'view_contact_modal',
        override: true
    };

    // Initialize the modal
    const contactModal = new Modal(viewContactModal, options, instanceOptions);

    // Function to open the contact modal
    window.openContactModal = function() {
        contactModal.show();
    };

    window.closeContactModal = function(){
        contactModal.hide();
    };

    window.loadContactData = async function(id){
        console.log(id);
        try {
            const data = await (await fetch(`${baseURL}/api/contacts/${id}`)).json();
            console.log(data);
            document.querySelector("#contact_name").innerHTML = data.name;
            document.querySelector("#contact_email").innerHTML = data.email;
            document.querySelector("#contact_image").src = data.picture;
            document.querySelector("#contact_address").innerHTML = data.address;
            document.querySelector("#contact_phone").innerHTML = data.phoneNumber;
            document.querySelector("#contact_about").innerHTML = data.description;
            const contactFavorite = document.querySelector("#contact_favorite");
            if (data.favorite) {
              contactFavorite.innerHTML =
                "<i class='fas fa-star text-yellow-400'></i><i class='fas fa-star text-yellow-400'></i><i class='fas fa-star text-yellow-400'></i><i class='fas fa-star text-yellow-400'></i><i class='fas fa-star text-yellow-400'></i>";
            } else {
              contactFavorite.innerHTML = "Not Favorite Contact";
            }
        
            document.querySelector("#contact_website").href = data.websitelink;
            document.querySelector("#contact_website").innerHTML = data.websitelink;
            document.querySelector("#contact_linkedIn").href = data.LinkednLink;
            document.querySelector("#contact_linkedIn").innerHTML = data.LinkednLink;
            openContactModal();
          } catch (error) {
            console.log("Error: ", error);
            }
            
    }

    //delete contact

    window.deleteContact = async function(id){
        Swal.fire({
            title: "Do you want to delete the contact?",
            showCancelButton: true,
            confirmButtonText: "delete",
            
          }).then((result) => {
            /* Read more about isConfirmed, isDenied below */
            if (result.isConfirmed) {
              Swal.fire("Saved!", "", "success");
            } else if (result.isDenied) {
              Swal.fire("Changes are not saved", "", "info");
            }
          });
    }
});

