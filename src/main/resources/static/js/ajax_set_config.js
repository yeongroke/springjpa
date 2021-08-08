jQuery.ajaxSettings.traditional = true;

$.ajaxSetup({
    beforeSend: function(xhr){
        xhr.setRequestHeader("ajax", "true");
    },
    error : function(xhr, status, errorThrown){

        switch (xhr.status) {
            case 403:
                window.location = '/signIn';
                break;
            default :
                console.log("xhr", xhr);
                console.log("status", status);
                console.log("errorThrown", errorThrown);
                alert("error: " + xhr.status + status);

        }
    }
})
