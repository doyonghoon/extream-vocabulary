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
});

function createVocaBodyDom(jquery, text, isNumeric) {
    var c = isNumeric ? '' : 'mdl-data-table__cell--non-numeric';
    return jquery('<th />', {
	'class': c,
	text: text
    });
};
