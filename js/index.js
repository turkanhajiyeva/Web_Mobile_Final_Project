document.getElementById("searchInput").addEventListener("keypress", function(event) {
    if (event.key === "Enter") {
        event.preventDefault();
        const query = this.value.trim();
        if (query !== "") {
            alert("You searched for: " + query);
        }
    }
});
