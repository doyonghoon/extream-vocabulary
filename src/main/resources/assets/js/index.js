$(function() {
    console.log('ready');
    // load voca items
    $.ajax('vocabulary')
	.done(function(data) {
	    var items = data.data;
	    for (i = 0; i < items.length; i++) {
		var vocaBodyHtml = $('<tr></tr>');
		var d = items[i];
		var frequencyDom = createVocaBodyDom($, d.frequency, true);
		var wordDom = createVocaBodyDom($, d.word, false);
		var synonymsDom = createVocaBodyDom($, d.synonyms, false);
		frequencyDom.appendTo(vocaBodyHtml);
		wordDom.appendTo(vocaBodyHtml);
		synonymsDom.appendTo(vocaBodyHtml);
		vocaBodyHtml.appendTo('#voca-table-body');
	    }
	})
	.fail(function() {
	    console.log('failed to load vocabulary');
	});

    $("form").submit(function(event) {
	event.preventDefault();
	console.log('submit');
	var wordValue = $('input#word').val();
	var frequencyValue = $('input#frequency').val();
	var synonymsValue = $('input#synonyms').val();

	$.post("vocabulary/create", {
	    word: wordValue,
	    frequency: frequencyValue,
	    synonyms: synonymsValue
	}).done(function(data) {
	    console.log('success result: ' + JSON.stringify(data));
	    $('input#word').val('');
	    $('input#frequency').val('');
	    $('input#synonyms').val('');
	    $('.add-textfield-layout').removeClass('is-dirty');

	}).fail(function(data) {
	    console.log('failed result: ' + JSON.stringify(data));
	});
    });
});

function createVocaBodyDom(jquery, text, isNumeric) {
    var c = isNumeric ? '' : 'mdl-data-table__cell--non-numeric';
    return jquery('<th />', {
	'class': c,
	text: text
    });
};
