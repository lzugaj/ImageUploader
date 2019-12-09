let x = setInterval(function() {
	let now = new Date().getTime();
	let date = new Date();
	let midnight = date.setHours(24,0,0,0);
	let t = midnight - now;
	let hours = Math.floor((t % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
	let minutes = Math.floor((t % (1000 * 60 * 60)) / (1000 * 60));
	let seconds = Math.floor((t % (1000 * 60)) / 1000);

	document.getElementById("demo").innerHTML = hours + "h " + minutes + "m " + seconds + "s ";
	if (t < 0) {
		clearInterval(x);
		document.getElementById("demo").innerHTML = "YOUR PACKAGE HAS EXPIRED!";
	}
}, 1000);