Playframework Multi-tenant PoC

Business Layer

All API calls to be invoked by the clients for publishing events will be protected and will uniquely identify the tenant using a JWT Token passed in the request header (tenant-key).

Action Composition - Playframework

A generic action will be be implemented to identify Tenats using the tenant-key header. Using Playframework's feature for Action Composition this functionality can be implemented in a generic way.

Tenant Key - JWT Token

JSON Web Tokens are an open, industry standard RFC 7519 method for representing claims securely between two parties.

Sample JWT Token

eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0ZW5hbnRfaWQiOiIxMjM0NTY3ODkwIiwic3RhdGlvbkNvZGUiOiJMRE4iLCJ3aW5kb3ciOiI4MSJ9.78aWeDjwDE0y8PqyLWSbAOt-hbtPPyVvpY92cM780YA

JWT Token Content

HEADER:ALGORITHM & TOKEN TYPE

{
  "alg": "HS256",
  "typ": "JWT"
}
PAYLOAD:DATA

{
  "tenant_id": "1234567890",
  "stationCode": "LDN",
  "window": "81"
}
VERIFY SIGNATURE
HMACSHA256(
  base64UrlEncode(header) + "." +
  base64UrlEncode(payload),  
  secret
) secret base64 encoded
