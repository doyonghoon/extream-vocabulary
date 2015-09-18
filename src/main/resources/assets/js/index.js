var vocaItems = [];
$(function() {
    // load voca items
    loadAllVocabulary();

    $("form").submit(function(event) {
	event.preventDefault();
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
    
    $("input#search").change(function(e) {
	var q = $(this).val();
	if (q.length > 0) {
	    loadVocabulary(q);
	} else {
	    vocaItems = [];
	    $('#voca-table-body').empty();
	    loadAllVocabulary();
	}
    });

    $('#start-game-button').click(function() {
	vocaItems = [];
	$('.demo-content').empty();
	$.get('vocabulary').done(function(data) {
	    for (i = 0; i < data.data.length; i++) {
		vocaItems.push(data.data[i]);
	    }
	    showNextVocaItem(vocaItems[0]);
	});
    });
});

function showNextVocaItem(item) {
    var content = $('.demo-content');
    var grid = $('<div />', {
	class: 'mdl-grid'
    });
    var grid2 = $('<div />', {
	class: 'mdl-grid'
    });
    var form = $('<form />', {
	action: '#'
    });
    var textfield = createGameTextField();
    var titleRow = createGridRow();
    var fieldRow = createGridRow();
    
    form.append(textfield);
    titleRow.append(createGameWord(item));
    fieldRow.append(form);
    
    grid.append(titleRow);
    grid2.append(fieldRow);
    content.append(grid);
    content.append(grid2);
};

function createGridRow() {
    return $('<div />', {class: 'mdl-cell mdl-cell-12-col'});
}

function createGameTextField() {
    var container = $('<div />', {
	class: 'mdl-textfield mdl-js-textfield is-upgraded',
	id: 'game-textfield',
	'data-upgraded': ',MaterialTextfield'
    });
    var label = $('<label />', {
	class: 'mdl-textfield__label',
	for: 'game-textfield',
	text: 'put a synonym'
    });
    var input = $('<input />', {
	class: 'mdl-textfield__input',
	type: 'text',
	for: 'game-textfield'
    });
    input.appendTo(container);
    label.appendTo(container);
    return container;
}

function createGameWord(item) {
    var word = item.word;
    return $('<h1 />', {
	text: word
    });
};

function loadAllVocabulary() {
    $.ajax('vocabulary')
	.done(function(data) {
	    updateGlobalVocaItems(data);
	    updateTableDom($);
	})
	.fail(function() {
	    vocaItems = [];
	});
}

function loadVocabulary(query) {
    if (query !== 'undefined' && query.length > 0) {
	$.get('vocabulary', {
	    word: query
	}).done(function(data) {
	    console.log(JSON.stringify(data.data));
	    vocaItems = [];
	    vocaItems.push(data.data);
	    $('#voca-table-body').empty();
	    updateTableDom($);
	    console.log('updated!');
	}).fail(function(data) {
	    console.log('fail: ' + JSON.stringify(data));
	});
    }
};

function updateGlobalVocaItems(data) {
    vocaItems = [];
    for (i = 0; i < data.data.length; i++) {
	var d = data.data[i];
	vocaItems.push(d);
    }
}

function createVocaBodyDom(jquery, text, isNumeric) {
    var c = isNumeric ? '' : 'mdl-data-table__cell--non-numeric';
    return jquery('<th />', {
	'class': c,
	text: text
    });
};

function updateTableDom(jquery) {
    for (i = 0; i < vocaItems.length; i++) {
	var vocaBodyHtml = jquery('<tr></tr>');
	var d = vocaItems[i];
	var frequencyDom = createVocaBodyDom(jquery, d.frequency, true);
	var wordDom = createVocaBodyDom(jquery, d.word, false);
	var synonymsDom = createVocaBodyDom(jquery, d.synonyms, false);
	frequencyDom.appendTo(vocaBodyHtml);
	wordDom.appendTo(vocaBodyHtml);
	synonymsDom.appendTo(vocaBodyHtml);
	vocaBodyHtml.appendTo('#voca-table-body');
    }
};
