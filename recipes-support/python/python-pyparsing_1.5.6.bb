DESCRIPTION = "A Python Parsing Module"
HOMEPAGE = "https://pypi.org"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${WORKDIR}/usr/share/doc/pyparsing-1.5.6/LICENSE;md5=657a566233888513e1f07ba13e2f47f1"
DEPENDS = "python"

SRC_URI = "http://mirror.centos.org/centos/7/os/x86_64/Packages/pyparsing-1.5.6-9.el7.noarch.rpm"
SRC_URI[md5sum] = "99039af6f690460e7234febe9228a66b"
SRC_URI[sha256sum] = "44987aea8a0b5d8d47a0aa31a6b90be291a8f9d1162e656a14736312f13a604d"

S = "${WORKDIR}/pyparsing-${PV}"

inherit allarch

RDEPENDS_${PN} += "python-lang python-shell python-io"

S = "${WORKDIR}"

PACKAGES = "${PN}"
FILES_${PN} += "/usr"

# Skip the unwanted steps
do_configure[noexec] = "1"
do_compile[noexec] = "1"

# Install the files to ${D}
do_install () {
    # Do it carefully
    [ -d "${S}" ] || exit 1
    cd ${S} || exit 1
    tar --no-same-owner -cpf - usr \
        | tar --no-same-owner -xpf - -C ${D}
}
