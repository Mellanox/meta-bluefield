SUMMARY = "Mellanox BlueField boot images"
HOMEPAGE = ""

LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://${WORKDIR}/COPYING;md5=d694b57fe8981242ee927f5639ce060d"

inherit bf_versioned

DIRFILES = "1"

COMPATIBLE_HOST = "aarch64.*-linux"

BASE_BOOTDATA = "${WORKDIR}/bootimages"
BOOTDATA = "${BASE_BOOTDATA}/lib/firmware/mellanox/boot"

SRC_URI = "file://COPYING \
           file://mlxbf-bootimages-1.0.tar.xz;subdir=${BOOTDATA}"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install () {
    [ -d "${BOOTDATA}" ] || exit 1
    cd ${BASE_BOOTDATA} || exit 1

    tar --no-same-owner --exclude='.debug' --exclude='./patches' \
        -cpf - . \
        | tar --no-same-owner -xpf - -C ${D}
}

PACKAGES = "${PN}"

FILES_${PN} = "/"
