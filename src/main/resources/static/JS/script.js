document,querySelector('.like-button').addEventListener('click',function(){
	this.classList.add('pulse-animation');
	setTime(() => {
		this.classList.remove('pulse-animation');
	}, 600);
});

$(document).ready(function () {
    // 画像をクリックしたときの処理
    $('.main-img, .sub-img').click(function () {
        // 選択された画像のソースを取得
        var imageUrl = $(this).attr('src');

        // モーダルの中の画像に選択された画像のソースを設定
        $('#modal-image').attr('src', imageUrl);

        // モーダルを表示
        $('#image-modal').modal('show');
    });
});
