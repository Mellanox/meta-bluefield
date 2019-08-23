DESCRIPTION = "An administration shell for configuring iSCSI, FCoE, and other SCSI targets, using the TCM/LIO kernel target subsystem."
SUMMARY = "An administration shell for storage targets"
SECTION = "targetcli"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/usr/share/doc/targetcli-2.1.fb46/COPYING;md5=1dece7821bf3fd70fe1309eaa37d52a2"


PV = "2.1.fb46"
SRC_URI = "http://mirror.centos.org/centos/7/os/x86_64/Packages/targetcli-2.1.fb46-7.el7.noarch.rpm"
SRC_URI[md5sum] = "3c2d213ab933ac95655f904a064b7697"
SRC_URI[sha256sum] = "b175c4dac01cbe8eb761632a08d6984d8fc894a2625d455223380f8ac52616f0"

inherit allarch

RDEPENDS_${PN} += "python python-dbus python-pyparsing python-rtslib-fb python-six python-configshell-fb python-pyudev"


S = "${WORKDIR}"

PACKAGES = "${PN}"
FILES_${PN} += "/usr"
FILES_${PN} += "/etc"

# Skip the unwanted steps
do_configure[noexec] = "1"
do_compile[noexec] = "1"

# Install the files to ${D}
do_install () {
    # Do it carefully
    [ -d "${S}" ] || exit 1
    cd ${S} || exit 1
    tar --no-same-owner -cpf - usr etc \
        | tar --no-same-owner -xpf - -C ${D}
}
