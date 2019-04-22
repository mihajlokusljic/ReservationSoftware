package rs.ac.uns.ftn.isa9.tim8.model;

public class UserTokenState {
	
    private String accessToken;
    private Long expiresIn;
    private TipKorisnika tipKorisnika;
    private String redirectionUrl;

    public UserTokenState() {
        this.accessToken = null;
        this.expiresIn = null;
        this.tipKorisnika = null;
        this.redirectionUrl = "#";
    }

    public UserTokenState(String accessToken, long expiresIn, TipKorisnika tipKor) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.tipKorisnika = tipKor;
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
    
    
}