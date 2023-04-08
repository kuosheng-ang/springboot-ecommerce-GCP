$(function() {
    $("a.confirmDeletion").click(function() {
        if (!confirm("Confirm Deletion")) return false;
    });
    if ($("#content").length) {
        ClassicEditor
            .create(document.querySelector("#content"))
            .catch(error => {
                console.log(error);
            });
    }
    if ($("#description").length) {
        ClassicEditor
            .create(document.querySelector("#description"))
            .catch(error => {
                console.log(error);
            });
    }
});

function readURL(input, idNum) {
    if (input.files && input.files[0]) {
        let reader = new FileReader();
        reader.onload = function(e) {
            $("imgPreview" + idNum).attr("src", e.target.result).width(100).height(100);
        }
        reader.readAsDataURL(input.files[0]);
    }
};

paypal.Buttons({
    // Sets up the transaction when a payment button is clicked
    createOrder: function(data, actions) {
      // Set up the transaction
      return actions.order.create({
        purchase_units: [{
          amount: {
            value: '0.01'
          }
        }]
      });
    },
    // Finalize the transaction after payer approval
    onApprove: (data, actions) => {
      return actions.order.capture().then(function(orderData) {
      // Successful capture! For dev/demo purposes:
        console.log('Capture result', orderData, JSON.stringify(orderData, null, 2));
        const transaction = orderData.purchase_units[0].payments.captures[0];
        alert(`Transaction ${transaction.status}: ${transaction.id}\n\nSee console for all available details`);
        // When ready to go live, remove the alert and show a success message within this page. For example:
        // const element = document.getElementById('paypal-button-container');
        // element.innerHTML = '<h3>Thank you for your payment!</h3>';
        // Or go to another URL:  actions.redirect('thank_you.html');
      });
    },
    createSubscription: function(data, actions) {
      return actions.subscription.create({
        'plan_id': 'P-2UF78835G6983425GLSM44MA'
      });
    },
    onApprove: function(data, actions) {
      alert('You have successfully created subscription ' + data.subscriptionID);
    }
}).render('#paypal-button-container');