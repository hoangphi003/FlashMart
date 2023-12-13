let FlashMart = angular.module('FlashMart', ['ngRoute']);

FlashMart.config(function($routeProvider) {
	$routeProvider
		.when('/', {
			templateUrl: '/assets/user/home.html',
			controller: 'ListProduct'
		})
		.when('/detail/:id/:categoryId/', {
			templateUrl: '/assets/user/chitiet.html',
			controller: 'FindProductById'
		})
		.when('/categoryNav/:id', {
			templateUrl: '/assets/user/category.html',
			controller: 'getAllCategoryById'
		})
		.when('/SearchProduct', {
			templateUrl: '/assets/user/SearchHome.html',
			controller: 'getProductByKeyword'
		})
		.when('/checkout', {
			templateUrl: '/assets/user/checkout.html',
			controller: 'checkout-ctrl'
		})
		.when('/purchase', {
			templateUrl: '/assets/user/purchase.html',
			controller: 'purchase-ctrl'
		})
		.when('/myorder', {
			templateUrl: '/assets/user/myorder.html',
			controller: 'myorder-ctrl'
		})
		.when('/cart', {
			templateUrl: '/assets/user/cart.html',
			controller: 'shopping-cart-ctrl'
		})
		.when('/login', {
			templateUrl: '/assets/user/login.html',
			controller: 'loginController'
		})
		.when('/register', {
			templateUrl: '/assets/user/register.html',
			controller: 'registerController'
		})
		.when('/qaa', {
			templateUrl: '/assets/user/FAQs.html'
		})
		.when('/blog', {
			templateUrl: '/assets/user/trangtintuc.html'
		})
		.otherwise({
			template: "<h1> 404 </h1>"
		})
});

//diplay all product from db
FlashMart.controller('ListProduct', function($scope, $http, $window) {
	$scope.list = [];
	$scope.form = {};
	$scope.token = $window.localStorage.getItem('userToken');

	try {
		if ($scope.token) {
			// Parse the token as JSON
			const tokenObject = JSON.parse($scope.token);

			// Assuming the email is stored in the token's 'email' property
			$scope.email = tokenObject.token.email;
			$scope.fullname = tokenObject.token.fullname;
			$scope.phone = tokenObject.token.phone;
			// Redirect to the server endpoint with the token as a query parameter
		}
	} catch (error) {
		// Handle parsing errors
		console.error('Error parsing token:', error);

	}

	$http.get('/user/product').then(Response => {
		$scope.list = Response.data;
	}).catch(error => error);

	$http.get('/user/productnew').then(Response => {
		$scope.listnew = Response.data;
	}).catch(error => error);

	$http.get('/user/category').then(Response => {
		$scope.cate = Response.data;
	}).catch(error => error);

	$scope.messageFail = function() {
		alert('Đổi thất bại');
	}

	$scope.mailer = function() {
		const alert = document.getElementById('alert');
		const gmail = document.getElementById('email').value;
		if (!gmail == "") {
			$http.put('/user/getpassword/' + $scope.form.email)
				.then(Response => {
					alert.classList.remove('d-none');
				}).catch(err => {
					err;
					alert('Đổi thất bại');
				});
		} else {
			$scope.messageFail();
		}
	}

	$scope.logOffToken = function() {
		localStorage.removeItem('userToken');
		window.location.reload();
	}

	// gọi lại giỏ hàng
	var url = "/user/product";
	var url1 = "/rest/orders";
	$scope.cart = {
		list: [],
		add(id) {
			var item = this.list.find(item => item.id == id);
			if (item) {
				item.qty++;
				this.saveToLocalStorage();
			}
			else {
				$http.get(`${url}/${id}`).then(resp => {
					resp.data.qty = 1;
					this.list.push(resp.data);
					this.saveToLocalStorage();
				})
			}

			alert("Đã thêm vào giỏ hàng")
		},
		remove(id) {
			var index = this.list.findIndex(item => item.id == id);
			this.list.splice(index, 1);// xoa phan tu
			this.saveToLocalStorage();
			$('.sanPham').html('<div style="text-align: center; font-size: 30px;color: gray;">Bạn chưa mua sản phẩm nào</div>');
		},
		clear() {
			this.list = [];
			this.saveToLocalStorage();
		},
		amt_of(item) { },
		// Tinh tong so luong cac mat hang trong gio
		get count() {
			return this.list
				.map(item => item.qty)
				.reduce((total, qty) => total += qty, 0);
		},
		// Tong thanh tien cac mat hang trong gio
		get amount() {
			return this.list
				.map(item => item.qty * item.price)
				.reduce((total, qty) => total += qty, 0);
		},
		saveToLocalStorage() {
			var json = JSON.stringify(angular.copy(this.list));
			localStorage.setItem("cart", json);
		},
		// Doc gio hang vao local storage
		loadFromLocalStorage() {
			var json = localStorage.getItem("cart");
			this.list = json ? JSON.parse(json) : [];
		}
	}
	$scope.cart.loadFromLocalStorage();
	$scope.order = {
		orderDate: new Date(),
		address: $scope.address,
		status: "Đang chờ xác nhận",

		get orderDetails() {
			return $scope.cart.list.map(item => {
				return {
					product: { id: item.id },
					price: item.price,
					quantity: item.qty
				}
			});
		},
		purchase() {
			var order = angular.copy(this);
			// Thực hiện đặt hàng
			$http.post(url1, order).then(resp => {
				alert("Đặt hàng thành công. Kiểm tra đơn hàng!");
				$scope.cart.clear();// xóa
			}).catch(error => {
				console.log("Error", error);
			})
		}
	}
});

//find product by id 
FlashMart.controller('FindProductById', function($scope, $http, $routeParams) {
	$http.get('/user/product/' + $routeParams.id).then(Response => {
		$scope.item = Response.data;
	}).catch(error => error);

	//find category by product id 
	$http.get('/user/productcategory/' + $routeParams.categoryId).then(Response => {
		$scope.itemc = Response.data;
	}).catch(error => error);
	var url = "/user/product";
	var url1 = "/rest/orders";
	$scope.cart = {
		list: [],
		add(id) {
			var item = this.list.find(item => item.id == id);
			if (item) {
				item.qty++;
				this.saveToLocalStorage();
			}
			else {
				$http.get(`${url}/${id}`).then(resp => {
					resp.data.qty = 1;
					this.list.push(resp.data);
					this.saveToLocalStorage();
				})
			}

			alert("Đã thêm vào giỏ hàng")
		},
		remove(id) {
			var index = this.list.findIndex(item => item.id == id);
			this.list.splice(index, 1);// xoa phan tu
			this.saveToLocalStorage();
			$('.sanPham').html('<div style="text-align: center; font-size: 30px;color: gray;">Bạn chưa mua sản phẩm nào</div>');
		},
		clear() {
			this.list = [];
			this.saveToLocalStorage();
		},
		amt_of(item) { },
		// Tinh tong so luong cac mat hang trong gio
		get count() {
			return this.list
				.map(item => item.qty)
				.reduce((total, qty) => total += qty, 0);
		},
		// Tong thanh tien cac mat hang trong gio
		get amount() {
			return this.list
				.map(item => item.qty * item.price)
				.reduce((total, qty) => total += qty, 0);
		},
		saveToLocalStorage() {
			var json = JSON.stringify(angular.copy(this.list));
			localStorage.setItem("cart", json);
		},
		// Doc gio hang vao local storage
		loadFromLocalStorage() {
			var json = localStorage.getItem("cart");
			this.list = json ? JSON.parse(json) : [];
		}
	}
	$scope.cart.loadFromLocalStorage();
	$scope.order = {
		orderDate: new Date(),
		address: $scope.address,
		status: "Đang chờ xác nhận",

		get orderDetails() {
			return $scope.cart.list.map(item => {
				return {
					product: { id: item.id },
					price: item.price,
					quantity: item.qty
				}
			});
		},
		purchase() {
			var order = angular.copy(this);
			// Thực hiện đặt hàng
			$http.post(url1, order).then(resp => {
				alert("Đặt hàng thành công. Kiểm tra đơn hàng!");
				$scope.cart.clear();// xóa
				location.href = "/order/detail/" + resp.data.id; // chuyển đến tảng chi tiết dơn hàng
			}).catch(error => {
				console.log("Error", error);
			})
		}
	}
});

FlashMart.controller('getAllCategoryById', function($scope, $http, $routeParams) {
	$http.get('/user/productcategory/' + $routeParams.id).then(Response => {
		$scope.item_category = Response.data;
	}).catch(error => error);
	var url = "/user/product";
	var url1 = "/rest/orders";
	$scope.cart = {
		list: [],
		add(id) {
			var item = this.list.find(item => item.id == id);
			if (item) {
				item.qty++;
				this.saveToLocalStorage();
			}
			else {
				$http.get(`${url}/${id}`).then(resp => {
					resp.data.qty = 1;
					this.list.push(resp.data);
					this.saveToLocalStorage();
				})
			}
			alert("Đã thêm vào giỏ hàng")
		},
		remove(id) {
			var index = this.list.findIndex(item => item.id == id);
			this.list.splice(index, 1);// xoa phan tu
			this.saveToLocalStorage();
			$('.sanPham').html('<div style="text-align: center; font-size: 30px;color: gray;">Bạn chưa mua sản phẩm nào</div>');
		},
		clear() {
			this.list = [];
			this.saveToLocalStorage();
		},
		amt_of(item) { },
		// Tinh tong so luong cac mat hang trong gio
		get count() {
			return this.list
				.map(item => item.qty)
				.reduce((total, qty) => total += qty, 0);
		},
		// Tong thanh tien cac mat hang trong gio
		get amount() {
			return this.list
				.map(item => item.qty * item.price)
				.reduce((total, qty) => total += qty, 0);
		},
		saveToLocalStorage() {
			var json = JSON.stringify(angular.copy(this.list));
			localStorage.setItem("cart", json);
		},
		// Doc gio hang vao local storage
		loadFromLocalStorage() {
			var json = localStorage.getItem("cart");
			this.list = json ? JSON.parse(json) : [];
		}
	}
	$scope.cart.loadFromLocalStorage();
	$scope.order = {
		orderDate: new Date(),
		address: $scope.address,
		status: "Đang chờ xác nhận",

		get orderDetails() {
			return $scope.cart.list.map(item => {
				return {
					product: { id: item.id },
					price: item.price,
					quantity: item.qty
				}
			});
		},
		purchase() {
			var order = angular.copy(this);
			// Thực hiện đặt hàng
			$http.post(url1, order).then(resp => {
				alert("Đặt hàng thành công. Kiểm tra đơn hàng!");
				$scope.cart.clear();// xóa
				location.href = "/order/detail/" + resp.data.id; // chuyển đến tảng chi tiết dơn hàng
			}).catch(error => {
				console.log("Error", error);
			})
		}
	}
});

FlashMart.controller('getProductByKeyword', function($scope, $http) {
	$http.get('/user/productsearch/' + $scope.form.keyword).then(Response => {
		$scope.result = Response.data;
		console.log($scope.form.keyword);
	}).catch(error => error);
	var url = "/user/product";
	var url1 = "/rest/orders";
	$scope.cart = {
		list: [],
		add(id) {
			var item = this.list.find(item => item.id == id);
			if (item) {
				item.qty++;
				this.saveToLocalStorage();
			}
			else {
				$http.get(`${url}/${id}`).then(resp => {
					resp.data.qty = 1;
					this.list.push(resp.data);
					this.saveToLocalStorage();
				});

			};
			alert("Đã thêm vào giỏ hàng")
		},
		remove(id) {
			var index = this.list.findIndex(item => item.id == id);
			this.list.splice(index, 1);// xoa phan tu
			this.saveToLocalStorage();
			$('.sanPham').html('<div style="text-align: center; font-size: 30px;color: gray;">Bạn chưa mua sản phẩm nào</div>');
		},
		clear() {
			this.list = [];
			this.saveToLocalStorage();
		},
		amt_of(item) { },
		// Tinh tong so luong cac mat hang trong gio
		get count() {
			return this.list
				.map(item => item.qty)
				.reduce((total, qty) => total += qty, 0);
		},
		// Tong thanh tien cac mat hang trong gio
		get amount() {
			return this.list
				.map(item => item.qty * item.price)
				.reduce((total, qty) => total += qty, 0);
		},
		saveToLocalStorage() {
			var json = JSON.stringify(angular.copy(this.list));
			localStorage.setItem("cart", json);
		},
		// Doc gio hang vao local storage
		loadFromLocalStorage() {
			var json = localStorage.getItem("cart");
			this.list = json ? JSON.parse(json) : [];
		}
	}
	$scope.cart.loadFromLocalStorage();
	$scope.order = {
		orderDate: new Date(),
		address: $scope.address,
		status: "Đang chờ xác nhận",

		get orderDetails() {
			return $scope.cart.list.map(item => {
				return {
					product: { id: item.id },
					price: item.price,
					quantity: item.qty
				}
			});
		},
		purchase() {
			var order = angular.copy(this);
			// Thực hiện đặt hàng
			$http.post(url1, order).then(resp => {
				alert("Đặt hàng thành công. Kiểm tra đơn hàng!");
				$scope.cart.clear();// xóa
				location.href = "/order/detail/" + resp.data.id; // chuyển đến tảng chi tiết dơn hàng
			}).catch(error => {
				console.log("Error", error);
			})
		}
	}
});

FlashMart.controller('loginController', function($scope, $http, $window) {
	$scope.form = {};
	$scope.errorMessages = { username: null, password: null };

	$scope.login = function() {
		$scope.errorMessages = { username: null, password: null };
		$scope.successMessages = {};

		if (!$scope.form.username || !$scope.form.password) {
			// Display an error message for each empty field
			if (!$scope.form.username) {
				$scope.errorMessages.username = "Nhập Username";
			}
			if (!$scope.form.password) {
				$scope.errorMessages.password = "Nhập mật khẩu";
			}
			return;
		}
		$http.post('/user/login', $scope.form, { headers: { 'Content-Type': 'application/json' } })
			.then(Response => {
				alert("Đăng nhập thành công")
				// Store token and user information in local storage
				$window.localStorage.setItem('userToken', JSON.stringify({ token: Response.data }));

				window.location.reload();
			})
			.catch(error => {
				if (error.status === 401) {
					$scope.errorMessages.username = "Invalid email or password";
					$scope.errorMessages.password = "Invalid email or password";
				} else {
					console.error("your found", error.data); // Log other errors
					$scope.errorMessages.username = "Tài khoản không hợp lệ";
					$scope.errorMessages.password = "Mật khẩu không hợp lệ";
				}
			});
	};
});

FlashMart.controller('registerController', function($scope, $http) {
	$scope.form = {};
	console.log($scope.form);
	$scope.errorMessages = {
		username: null,
		fullname: null,
		phone: null,
		email: null,
		gender: null,
		password: null,
	};

	$scope.register = function() {
		$scope.successMessages = {};
		$scope.errorMessages = {
			username: null,
			fullname: null,
			phone: null,
			email: null,
			gender: null,
			password: null,
		};

		if (!$scope.form.fullname || !$scope.form.phone ||
			!$scope.form.email || !$scope.form.gender ||
			!$scope.form.password || !$scope.form.username) {
			// Display an error message for each empty field
			if (!$scope.form.username) {
				$scope.errorMessages.username = "Nhập username";
			}
			if (!$scope.form.fullname) {
				$scope.errorMessages.fullname = "Nhập Tên";
			}
			if (!$scope.form.email) {
				$scope.errorMessages.email = "Nhập mail";
			}
			if (!$scope.form.phone) {
				$scope.errorMessages.phone = "Nhập số điện thoại";
			}
			if (!$scope.form.gender) {
				$scope.errorMessages.gender = "Chọn giới tính";
			}
			if (!$scope.form.password) {
				$scope.errorMessages.password = "Nhập mật khẩu";
			}
			return;
		};
		$http.post('/user/register', $scope.form, { headers: { 'Content-Type': 'application/json' } })
			.then(Response => {
				console.log('Form Data:', $scope.form);
				Response.data;
				$scope.successMessages.info = "Đăng ký thành công";
			}).catch(error => {
				console.error(error);
				$scope.errorMessages.info = 'Có lỗi xảy ra';
			});
	}
});

FlashMart.controller("shopping-cart-ctrl", function($scope, $http, $window) {
	var url = "/user/product";
	var url1 = "/rest/orders";
	$scope.cart = {
		list: [],
		add(id) {
			var item = this.list.find(item => item.id == id);
			if (item) {
				item.qty++;
				this.saveToLocalStorage();
			}
			else {
				$http.get(`${url}/${id}`).then(resp => {
					resp.data.qty = 1;
					this.list.push(resp.data);
					this.saveToLocalStorage();
				})
			}
		},
		remove(id) {
			var index = this.list.findIndex(item => item.id == id);
			this.list.splice(index, 1);
			this.saveToLocalStorage();
			$('.sanPham').html('<div style="text-align: center; font-size: 30px;color: gray;">Bạn chưa mua sản phẩm nào</div>');
		},
		clear() {
			this.list = [];
			this.saveToLocalStorage();
		},
		amt_of(item) { },
		get count() {
			return this.list
				.map(item => item.qty)
				.reduce((total, qty) => total += qty, 0);
		},
		get amount() {
			return this.list
				.map(item => item.qty * item.price)
				.reduce((total, qty) => total += qty, 0);
		},
		saveToLocalStorage() {
			var json = JSON.stringify(angular.copy(this.list));
			localStorage.setItem("cart", json);
		},
		// Doc gio hang vao local storage
		loadFromLocalStorage() {
			var json = localStorage.getItem("cart");
			this.list = json ? JSON.parse(json) : [];
		}
	}
	$scope.cart.loadFromLocalStorage();
	$scope.token = $window.localStorage.getItem('userToken');
	try {
		if ($scope.token) {
			// Parse the token as JSON
			var tokenObject = JSON.parse($scope.token);

			// Assuming the email is stored in the token's 'email' property
			$scope.username = tokenObject.token.username;
			// Redirect to the server endpoint with the token as a query parameter
		}
	} catch (error) {
		// Handle parsing errors
		console.error('Error parsing token:', error);

	}
	$scope.order = {
		orderDate: new Date(),
		address: '',
		account: $scope.username,
		status: "Đang chờ xác nhận",

		get orderDetails() {
			return $scope.cart.list.map(item => {
				return {
					product: { id: item.id },
					price: item.price,
					quantity: item.qty
				}
			});
		},

		purchase() {
			$scope.order.account = $scope.username;
			$scope.order.address = $scope.address;
			$scope.errorMessages = {
				fullname: null, phone: null,
				email: null, address: null
			};
			var order = angular.copy(this);

			// Thực hiện đặt hàng
			$http.post(url1, order).then(resp => {
				alert("Đặt hàng thành công. Kiểm tra đơn hàng!");
				$scope.cart.clear();// xóa
			}).catch(error => {
				console.log("Error", error);
			});

		}
	}
});

FlashMart.controller("checkout-ctrl", function($scope, $http, $window) {
	var url = "/user/product";
	var url1 = "/rest/orders";
	/*	$scope.errorMessages = {
			fullname: null, phone: null,
			email: null, address: null
		};*/
	$scope.cart = {
		list: [],
		add(id) {
			var item = this.list.find(item => item.id == id);
			if (item) {
				item.qty++;
				this.saveToLocalStorage();
			}
			else {
				$http.get(`${url}/${id}`).then(resp => {
					resp.data.qty = 1;
					this.list.push(resp.data);
					this.saveToLocalStorage();
				})
			}
		},
		remove(id) {
			var index = this.list.findIndex(item => item.id == id);
			this.list.splice(index, 1);
			this.saveToLocalStorage();
			$('.sanPham').html('<div style="text-align: center; font-size: 30px;color: gray;">Bạn chưa mua sản phẩm nào</div>');
		},
		clear() {
			this.list = [];
			this.saveToLocalStorage();
		},
		amt_of(item) { },
		get count() {
			return this.list
				.map(item => item.qty)
				.reduce((total, qty) => total += qty, 0);
		},
		get amount() {
			return this.list
				.map(item => item.qty * item.price)
				.reduce((total, qty) => total += qty, 0);
		},
		saveToLocalStorage() {
			var json = JSON.stringify(angular.copy(this.list));
			localStorage.setItem("cart", json);
		},
		// Doc gio hang vao local storage
		loadFromLocalStorage() {
			var json = localStorage.getItem("cart");
			this.list = json ? JSON.parse(json) : [];
		}
	}
	$scope.cart.loadFromLocalStorage();
	$scope.token = $window.localStorage.getItem('userToken');
	try {
		if ($scope.token) {
			// Parse the token as JSON
			var tokenObject = JSON.parse($scope.token);

			// Assuming the email is stored in the token's 'email' property
			$scope.username = tokenObject.token.username;
			// Redirect to the server endpoint with the token as a query parameter
		}
	} catch (error) {
		// Handle parsing errors
		console.error('Error parsing token:', error);

	}
	$scope.order = {
		orderDate: new Date(),
		address: '',
		account: $scope.username,
		status: "Đang chờ xác nhận",

		get orderDetails() {
			return $scope.cart.list.map(item => {
				return {
					product: { id: item.id },
					price: item.price,
					quantity: item.qty,
					total: item.qty * item.price
				}
			});
		},

		purchase() {
			$scope.order.account = $scope.username;
			$scope.order.address = $scope.address;
			/*$scope.errorMessages = {
				fullname: null, phone: null,
				email: null, address: null
			};
			if (!$scope.fullname || !$scope.phone || !$scope.email || !$scope.address) {
				// Display an error message for each empty field
				if (!$scope.fullname) {
					$scope.errorMessages.fullname = "Nhập họ và tên";
				}
				if (!$scope.phone) {
					$scope.errorMessages.phone = "Nhập số điện thoại";
				}
				if (!$scope.email) {
					$scope.errorMessages.email = "Nhập email";
				}
				if (!$scope.address) {
					$scope.errorMessages.address = "Nhập địa chỉ";
				}
				return;
			} */
			var order = angular.copy(this);
			// Thực hiện đặt hàng
			$http.post(url1, order).then(resp => {
				alert("Đặt hàng thành công. Kiểm tra đơn hàng!");
				$scope.cart.clear();// xóa
			}).catch(error => {
				console.log("Error", error);
			});
		}
	}
});

FlashMart.controller("purchase-ctrl", function($scope, $http, $window) {
	var url = "/user/product";
	var url1 = "/rest/orders";
	/*	$scope.errorMessages = {
			fullname: null, phone: null,
			email: null, address: null
		};*/
	$scope.cart = {
		list: [],
		add(id) {
			var item = this.list.find(item => item.id == id);
			if (item) {
				item.qty++;
				this.saveToLocalStorage();
			}
			else {
				$http.get(`${url}/${id}`).then(resp => {
					resp.data.qty = 1;
					this.list.push(resp.data);
					this.saveToLocalStorage();
				})
			}
		},
		remove(id) {
			var index = this.list.findIndex(item => item.id == id);
			this.list.splice(index, 1);
			this.saveToLocalStorage();
			$('.sanPham').html('<div style="text-align: center; font-size: 30px;color: gray;">Bạn chưa mua sản phẩm nào</div>');
		},
		clear() {
			this.list = [];
			this.saveToLocalStorage();
		},
		amt_of(item) { },
		get count() {
			return this.list
				.map(item => item.qty)
				.reduce((total, qty) => total += qty, 0);
		},
		get amount() {
			return this.list
				.map(item => item.qty * item.price)
				.reduce((total, qty) => total += qty, 0);
		},
		saveToLocalStorage() {
			var json = JSON.stringify(angular.copy(this.list));
			localStorage.setItem("cart", json);
		},
		// Doc gio hang vao local storage
		loadFromLocalStorage() {
			var json = localStorage.getItem("cart");
			this.list = json ? JSON.parse(json) : [];
		}
	}
	$scope.cart.loadFromLocalStorage();
	$scope.token = $window.localStorage.getItem('userToken');
	try {
		if ($scope.token) {
			// Parse the token as JSON
			var tokenObject = JSON.parse($scope.token);

			// Assuming the email is stored in the token's 'email' property
			$scope.username = tokenObject.token.username;
			// Redirect to the server endpoint with the token as a query parameter
		}
	} catch (error) {
		// Handle parsing errors
		console.error('Error parsing token:', error);

	}
	$scope.order = {
		orderDate: new Date(),
		address: '',
		account: $scope.username,
		status: "Đang chờ xác nhận",

		get orderDetails() {
			return $scope.cart.list.map(item => {
				return {
					product: { id: item.id },
					price: item.price,
					quantity: item.qty,
					total: item.price * item.qty
				}
			});
		},

		purchase() {
			$scope.order.account = $scope.username;
			$scope.order.address = $scope.address;
			/*$scope.errorMessages = {
				fullname: null, phone: null,
				email: null, address: null
			};
			if (!$scope.fullname || !$scope.phone || !$scope.email || !$scope.address) {
				// Display an error message for each empty field
				if (!$scope.fullname) {
					$scope.errorMessages.fullname = "Nhập họ và tên";
				}
				if (!$scope.phone) {
					$scope.errorMessages.phone = "Nhập số điện thoại";
				}
				if (!$scope.email) {
					$scope.errorMessages.email = "Nhập email";
				}
				if (!$scope.address) {
					$scope.errorMessages.address = "Nhập địa chỉ";
				}
				return;
			} */
			var order = angular.copy(this);
			// Thực hiện đặt hàng
			$http.post(url1, order).then(resp => {
				alert("Đặt hàng thành công. Kiểm tra đơn hàng. Kiểm tra đơn hàng!");
				$scope.cart.clear();// xóa
			}).catch(error => {
				console.log("Error", error);
			});
		}
	}
});
FlashMart.controller("myorder-ctrl", function($scope, $http, $window) {
	$scope.token = $window.localStorage.getItem('userToken');

	try {
		if ($scope.token) {
			// Parse the token as JSON
			const tokenObject = JSON.parse($scope.token);

			// Assuming the email is stored in the token's 'email' property
			$scope.username = tokenObject.token.username;
		}
	} catch (error) {
		// Handle parsing errors
		console.error('Error parsing token:', error);

	}
	$http.get('/rest/myorder/' + $scope.username).then(resp => {
		$scope.myorder = resp.data;
		console.log("API Response:", resp.data);
		console.log("day",$scope.myorder);

	}).catch(error => {
		console.log("Error", error);
	});

	$scope.cancel = {
		status: "Đã hủy",
		ok(id) {
			const confirmed = window.confirm("Bạn có chắc muốn hủy đơn hàng không?");
			if (confirmed) {
				let c = angular.copy(this);
				$http.put('/rest/orders/' + id, c).then(resp => {
					$window.location.reload();
				}).catch(error => {
					console.log("Error", error);
				});
			}
		}
	}
});


