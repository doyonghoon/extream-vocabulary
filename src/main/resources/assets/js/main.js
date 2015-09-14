Polymer({
    is: 'main-layout',
    ready: function() {
	console.log('ready');
    },
    handleClick: function() {
	this.$.vocalist.generateRequest();
    },
    hresponse: function(req) {
	console.log('res: ' + JSON.stringify(req.detail.response));
	if (req.detail.response.resultCode === 200) {
	    var items = [];
	    for (i = 0; i < req.detail.response.data.length; i++) {
		var v = req.detail.response.data[i];
		items.push(v);
	    }
	    this.vocaItems = items;
	    console.log("items: " + this.vocaItems.length);
	    this.notifyPath('vocaItems', this.vocaItems);
	}
    }
});
