Polymer({
    is: 'main-layout',
    ready: function() {
	console.log('ready');
	handleClick();
    },
    handleClick: function() {
	this.$.ajax.url = "http://google.com";
	this.$.ajax.params = {};
	this.$.ajax.generateRequest();
    },
    hresponse: function(req) {
	console.log('res: ' + JSON.stringify(req.detail.response));
    }
});
