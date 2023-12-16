document,querySelector('.like-button').addEventListener('click',function(){
	this.classList.add('pulse-animation');
	setTime(() => {
		this.classList.remove('pulse-animation');
	}, 600);
});
