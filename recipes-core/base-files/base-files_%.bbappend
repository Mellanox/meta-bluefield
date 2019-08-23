LICENSE = "BSD"
FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

LIC_FILES_CHKSUM += \
"file://${WORKDIR}/preinit.sh;beginline=3;endline=28;md5=af7c2555086a4dea14179634e1aff906"

SRC_URI += "file://preinit.sh"

do_install_append() {
    install -m 0755 -d ${D}/data
    install -m 0744 ${WORKDIR}/preinit.sh ${D}/sbin/preinit
}
