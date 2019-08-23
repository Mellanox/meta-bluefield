inherit externalsrc

SUMMARY = "Mellanox BlueField Boot Control tool"
HOMEPAGE = ""

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://mlxbf-bootctl.c;beginline=1;endline=29;md5=b7861d57cc6f43fb2a002a17241b5160"
DEPENDS = ""

PV = "1.0"
PR = "r1"

SRC_URI = "file://mlxbf-bootctl-1.0.tar.xz"

do_compile() {
        oe_runmake -f ${S}/Makefile
}

do_install () {
        oe_runmake -f ${S}/Makefile install DESTDIR=${D} SBINDIR=${base_sbindir}
}
