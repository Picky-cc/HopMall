//15[报告者] 25[观察者] 30［开发者］40［管理者］
// 列出所有项目地址的
$('.project').map(function(i,a){console.log("http://git.oschina.net/"+$(a).attr("href"));})

// 压缩前
var key="919905036@qq.com";

$.ajax({url:"http://git.oschina.net/users/search?q="+key+"&page=1&per_page=10&enterprise_id=0"}).done(function(data){

    var needContinue = true;

	if(data.length != 1){

		alert("填入的搜索关键字有误，请重新填入！");

		needContinue=false;
	}

	if(needContinue){

		var user_id=data[0].id;

		console.log("用户的ID［"+user_id+"］");
		var projectUrlAndAccessMap = {
			"http://git.oschina.net/trustno1/greenLight/":"25",
			"http://git.oschina.net/trustno1/MunichRe/":"25",
			"http://git.oschina.net/trustno1/demo2do-core/":"25",
			"http://git.oschina.net/trustno1/zufangbao-springboot-center/":"25",
			"http://git.oschina.net/trustno1/gluon/":"30",
			"http://git.oschina.net/trustno1/wellsfargo/":"30",
			"http://git.oschina.net//trustno1/canal-core/":"25",

			"http://git.oschina.net/myounique/berkshire/":"30",
			"http://git.oschina.net//myounique/coffer/":"30",
			"http://git.oschina.net//myounique/bridgewater-deduct/":"30",
			"http://git.oschina.net//myounique/sun/":"30",
			"http://git.oschina.net//myounique/earth/":"30",
			"http://git.oschina.net//zufangbaowk/PriceWaterHouse/":"30",
			"http://git.oschina.net//zufangbaowk/Renaissance/":"30"
		};

		for(var projectUrl in projectUrlAndAccessMap){

			window.open(projectUrl+"team_members/new");

			$.ajax({url:projectUrl+"team_members",type:"POST",data:{user_ids:user_id,utf8:"✓",project_access:projectUrlAndAccessMap[projectUrl],authenticity_token:$("input[name='authenticity_token']").val()}}).done(function(data){window.close();});
		}
	}

});
// 压缩后

var key="yuzheyu@hzsuidifu.com";$.ajax({url:"http://git.oschina.net/users/search?q="+key+"&page=1&per_page=10&enterprise_id=0"}).done(function(data){var needContinue=true;if(data.length!=1){alert("填入的搜索关键字有误，请重新填入！");needContinue=false}if(needContinue){var user_id=data[0].id;console.log("用户的ID［"+user_id+"］");var projectUrlAndAccessMap={"http://git.oschina.net/trustno1/greenLight/":"25","http://git.oschina.net/trustno1/MunichRe/":"25","http://git.oschina.net/trustno1/demo2do-core/":"25","http://git.oschina.net/trustno1/zufangbao-springboot-center/":"25","http://git.oschina.net/trustno1/gluon/":"30","http://git.oschina.net/trustno1/wellsfargo/":"30","http://git.oschina.net//trustno1/canal-core/":"25","http://git.oschina.net/myounique/berkshire/":"30","http://git.oschina.net//myounique/coffer/":"30","http://git.oschina.net//myounique/bridgewater-deduct/":"30","http://git.oschina.net//myounique/sun/":"30","http://git.oschina.net//myounique/earth/":"30","http://git.oschina.net//zufangbaowk/PriceWaterHouse/":"30","http://git.oschina.net//zufangbaowk/Renaissance/":"30"};for(var projectUrl in projectUrlAndAccessMap){window.open(projectUrl+"team_members/new");$.ajax({url:projectUrl+"team_members",type:"POST",data:{user_ids:user_id,utf8:"✓",project_access:projectUrlAndAccessMap[projectUrl],authenticity_token:$("input[name='authenticity_token']").val()}}).done(function(data){})}}});
