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
  <paper-header-panel main mode="waterfall-tall">

    <!-- Main Toolbar -->
    <paper-toolbar id="mainToolbar">
      <paper-icon-button id="paperToggle" icon="menu" paper-drawer-toggle></paper-icon-button>
      <span class="flex"></span>

      <!-- Toolbar icons -->
      <paper-icon-button icon="refresh"></paper-icon-button>
      <paper-icon-button icon="search"></paper-icon-button>

      <!-- Application name -->
      <div class="middle paper-font-display2 app-name">Under construction</div>

      <!-- Application sub title -->
      <div class="bottom title"></div>

    </paper-toolbar>
    <div class="content"></div>
  </paper-header-panel>
</body>
</html>
