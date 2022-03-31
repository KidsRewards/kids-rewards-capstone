$('.drop-menu ul').hide();
$(".drop-menu a").click(function () {
  $(this).parent(".drop-menu").children("ul").slideToggle("200");
  $(this).find("i.fa").toggleClass("fa-robot-angle-up fa-robot-angle-down");
});