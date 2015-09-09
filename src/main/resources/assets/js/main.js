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
    }
});
