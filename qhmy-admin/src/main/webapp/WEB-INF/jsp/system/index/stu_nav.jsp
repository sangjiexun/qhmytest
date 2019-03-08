<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>职工首页</title>
	</head>
	<body>
		<div class="jf_ctn container">
			<nav class="navbar jf_nav">
				<div class="navbar-header">
					<a href="${basepath}main/index" class="jf_navbra navbar-brand">
						<img src="${basepath}static/images/zhigong_sy/logo3.png" width="100%"/>
					</a>
				</div>
				<div class="collapse navbar-collapse">
					<ul class="nav navbar-nav navbar-right">
						<li class="jf_bell"><a class="jf_a" href="">
							<span class="fa fa-bell ">
								<span class="badge jf_xiaoxshu">6</span>
							</span>
						</a></li>
						<li><a class="jf_a" href="">${stuInfoPd.USERNAME}</a></li>
						<li><a class="jf_biga" href="${basepath}logout"><span class="fa fa-power-off"></span></a></li>
					</ul>
				</div>
			</nav>
		</div>	
	</body>
</html>
