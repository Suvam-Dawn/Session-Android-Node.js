var express = require("express");
var cookieParser = require("cookie-parser");
var bodyParser = require("body-parser");
var session = require("express-session");
var app = express();
app.use(bodyParser.json());
app.use(
  bodyParser.urlencoded({
    extended: false
  })
);
app.use(
  session({
    secret: "keyboard cat",
    resave: true,
    saveUninitialized: true
  })
);

app.use(cookieParser());

app.get("/", (req, res, next) => {
  console.log("hi");
  res.send(
    "Welcome Session_Android_Node.js Application.<br/> DEVELOPED BY SUVAM DAWN"
  );
});

app.post("/setSession", (req, res) => {
  req.session.sessionId = req.sessionID;
  req.session.userName = req.body.name;
  console.log({
    id: req.session.sessionId,
    name: req.session.userName
  });
  res.send({ id: req.session.sessionId, name: req.session.userName });
});
app.post("/getSession", (req, res) => {
  console.log({ id: req.session.sessionId, name: req.session.userName });
  res.send({
    id: req.sessionID,
    name: req.session.userName
  });
});
app.use(function(req, res, next) {
  var err = new Error("Not Found");
  err.status = 404;
  //next(err);
  res.render("404");
});

// error handler
app.use(function(err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get("env") === "development" ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.render("500");
});

module.exports = app;
