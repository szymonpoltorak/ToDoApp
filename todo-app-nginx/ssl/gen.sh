#!/bin/sh
# Example script for building self signed certificate signed by create CA
# Igor Kędzierawski 2023 && Szymon Półtorak 2023

# Private key for CA
CA_PRIVKEY="ca_server.key"

# Self Signed certificate for CA
CA_CERT="ca_server.crt"

# Private key for Server
SV_PRIVKEY="server.key"

# Sign request for Server
SV_SIGN_REQ="sv_sign_req.csr"

# Signed certificate for Server
SV_CERT="server.crt"

# Keystore file in PKCS12 fromat
SV_KEYSTORE="server.p12"

# Configuration file for server certificate
OPENSSL_CONF="openssl.conf"

# Keystore password
KEY_STORE_PASS=none

# Directory of frontend application
FRONTEND_DIR="todo-app-frontend"

# Directory of backend application
BACKEND_DIR="todo-app-backend"

# Generating certificate for our CA with example data
# Generating private key for CA

openssl genrsa -out "${CA_PRIVKEY}" 4096
[ "$?" = 0 ] || exit 1

# Generating self signed certificate for CA

openssl req -new -x509 -key "${CA_PRIVKEY}" -out "${CA_CERT}" <<EOF
DK
MistyMountains
Erebor
DwarvesOfErebor
DA
DwarvesOfLonelyMountain
kingOfTheMountain@dwarven-realm.north
EOF
[ "$?" = 0 ] || exit 1

# Generating certificate for server using created CA
## Generating private key for server

openssl genrsa -out "${SV_PRIVKEY}" 4096
[ "$?" = 0 ] || exit 1

# Generating configuration file for creating certificates

cat > "${OPENSSL_CONF}" << EOF
[req]
distinguished_name = req_distinguished_name
req_extensions = v3_req
prompt = no
[req_distinguished_name]
C = VA
ST = StackOverflow
L = SubjAltNameNotPresentInCert
O = jonashackt Corp
OU = Alices Dpt
CN = the-sacred-texts
[v3_req]
keyUsage = digitalSignature, keyEncipherment
extendedKeyUsage = serverAuth
subjectAltName = @alt_names
[alt_names]
DNS.1 = localhost
DNS.2 = server-alice.domain
DNS.3 = *.jakas.inna.domena
EOF
[ "$?" = 0 ] || exit 1

# Generating request for signing certificate using CA

openssl req -new -key "${SV_PRIVKEY}" -config "${OPENSSL_CONF}" -out "${SV_SIGN_REQ}"
[ "$?" = 0 ] || exit 1

# Sign the request using CA

openssl x509 -req -in "${SV_SIGN_REQ}" -extfile "${OPENSSL_CONF}" -extensions v3_req \
    -CA "${CA_CERT}" -CAkey "${CA_PRIVKEY}" -CAcreateserial -out "${SV_CERT}" -days 365 -sha512
[ "$?" = 0 ] || exit 1

# Bundling key and certificate into keystore file in PKCS12 fromat

openssl pkcs12 -export -out "${SV_KEYSTORE}" -inkey "${SV_PRIVKEY}" -in "${SV_CERT}" -passout pass:${KEY_STORE_PASS} && break

# Clean working files

rm ${SV_SIGN_REQ} ${OPENSSL_CONF}

# Moving files into directories
cp ${SV_CERT} ${SV_PRIVKEY} ../../${FRONTEND_DIR}/src/assets/ssl/

cp ${SV_CERT} ${SV_PRIVKEY} ../../${BACKEND_DIR}/src/main/resources/

echo "Result:
    Certificate Authority:
        Key:             ${CA_PRIVKEY}
        Certificate:     ${CA_CERT} (optionally install in browser)
    Signed Certificate for server using CA:
        Key:             ${SV_PRIVKEY}
        Certificate:     ${SV_CERT}
    Password for keystore : '${KEY_STORE_PASS}'
    Files have been moved to setup directories.
    "
