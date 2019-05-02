package rs.ac.uns.ftn.isa9.tim8.model;

public class UserTokenState {
	
    private String accessToken;
    private Long expiresIn;
    private TipKorisnika tipKorisnika;
    private String redirectionUrl;
    private boolean promjenjenaLozinka;
   
    
    public UserTokenState() {
        this.accessToken = null;
        this.expiresIn = null;
        this.tipKorisnika = null;
        this.redirectionUrl = "#";
        this.promjenjenaLozinka = false;
    }

    public UserTokenState(String accessToken, long expiresIn, TipKorisnika tipKor, boolean promjenjenaLozinka) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.tipKorisnika = tipKor;
        this.promjenjenaLozinka = promjenjenaLozinka;
    }
    
    public UserTokenState(String accessToken, long expiresIn, TipKorisnika tipKorisnika, String redirectionUrl) {
		super();
		this.accessToken = accessToken;
		this.expiresIn = expiresIn;
		this.tipKorisnika = tipKorisnika;
		this.redirectionUrl = redirectionUrl;
	}

	public String getRedirectionUrl() {
		return redirectionUrl;
	}

	public void setRedirectionUrl(String redirectionUrl) {
		this.redirectionUrl = redirectionUrl;
	}

	public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

	public TipKorisnika getTipKorisnika() {
		return tipKorisnika;
	}

	public void setTipKorisnika(TipKorisnika tipKorisnika) {
		this.tipKorisnika = tipKorisnika;
	}

	public boolean isPromjenjenaLozinka() {
		return promjenjenaLozinka;
	}

	public void setPromjenjenaLozinka(boolean promjenjenaLozinka) {
		this.promjenjenaLozinka = promjenjenaLozinka;
	}
    
	
    
}