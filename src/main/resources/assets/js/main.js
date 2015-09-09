Polymer({
  is: 'main-layout',
  ready: function() {
    console.log('ready');
    handleClick();
  },
  handleClick: function() {
      console.log("kick click");
      this.$.ajax.url = "http://google.com";
      this.$.ajax.params = {};
      this.$.ajax.generateRequest();
  },
      hresponse: function(req) {
      console.log('hresponse invoked!');
      console.log('res: ' + JSON.stringify(req.detail.response));
      }
 });
