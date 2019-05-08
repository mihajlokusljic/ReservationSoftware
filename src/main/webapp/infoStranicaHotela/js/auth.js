let token_key = "jwtToken";

function getJwtToken(){
	return localStorage.getItem(token_key);
}

function setJwtToken(token){
	localStorage.setItem(token_key, token);
}

function removeJwtToken(){
	localStorage.removeItem(token_key);
}

function createAuthorizationTokenHeader(){
	var token = getJwtToken(token_key)
	
	if(token){
		return {
            "Authorization": "Bearer " + token,
            'Content-Type': 'application/json'
          };
	} else{
		return {
            'Content-Type': 'application/json'
          };
	}
}