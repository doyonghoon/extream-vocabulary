<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, minimum-scale=1.0, initial-scale=1, user-scalable=yes">
    <title></title>

    <script src="bower_components/webcomponentsjs/webcomponents-lite.min.js"></script>

    <link rel="import" href="elements.html">

  <style>
    paper-toolbar + paper-toolbar {
      margin-top: 20px;
    }
  </style>

</head>
<body unresolved class="fullbleed layout vertical">
  <dom-module id="main-layout">
    <template>
      <paper-drawer-panel>
        <paper-header-panel drawer>
          <paper-toolbar>
            <div>Vocabulary</div>
          </paper-toolbar>
          <div class="separator">All items</div>
          <paper-menu selectable="paper-icon-item" selected="0" role="menu" tabindex="0">
            <iron-icon icon="inbox"></iron-icon>
            <paper-icon-item>Browse All Vocabularies</paper-icon-item>
          </paper-menu>
        </paper-header-panel>
        <paper-header-panel main mode="waterfall-tall">

          <!-- Main Toolbar -->
          <paper-toolbar id="main-toolbar">
            <paper-icon-button id="paper-toggle" icon="menu" paper-drawer-toggle></paper-icon-button>
            <span class="flex"></span>

            <!-- Toolbar icons -->
            <paper-icon-button on-tap="handleClick" id="refresh-menu" icon="refresh"></paper-icon-button>
            <paper-icon-button id="search-menu" icon="search"></paper-icon-button>
            <!-- Application name -->
            <div class="middle paper-font-display2 app-name">Under construction</div>

            <!-- Application sub title -->
            <div class="bottom title">bottom title goes here</div>
          </paper-toolbar>
          <div class="content"></div>
        </paper-header-panel>
      </paper-drawer-panel>
      <iron-ajax
	 auto
	 id="ajax"
	 url="vocabulary"
	 handle-as="json"
	 on-response="hresponse"
	 debounce-duration="300"></iron-ajax>
    </template>
    <script>
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
    </script>
  </dom-module>
  <main-layout></main-layout>
</body>
</html>
