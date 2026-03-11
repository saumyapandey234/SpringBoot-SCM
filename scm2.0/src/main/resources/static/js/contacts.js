console.log("contacts.js loaded ✅");

document.addEventListener("DOMContentLoaded", () => {
    const viewContactModal = document.getElementById('view_contact_modal');

    if (!viewContactModal) {
        console.error("❌ Modal element with id 'view_contact_modal' not found in DOM.");
        return;
    }

    // Flowbite options
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

    // Instance options
    const instanceOptions = {
        id: 'view_contact_modal',
        override: true
    };

    // Create Flowbite modal instance
    const contactModal = new Modal(viewContactModal, options, instanceOptions);

    // Expose functions globally so they work with inline onclick=""
    window.openContactModal = function () {
        contactModal.show();
    };

    window.closeContactModal = function () {
        contactModal.hide();
    };

    console.log("✅ Modal initialized successfully");
});




