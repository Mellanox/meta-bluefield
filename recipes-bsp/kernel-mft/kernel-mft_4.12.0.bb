SUMMARY = "Mellanox MFT kernel modules"
DESCRIPTION = "The Mellanox Firmware Tools (MFT) package is a set of firmware \
management and debug tools for Mellanox devices. MFT can be used for \
generating a standard or customized Mellanox firmware image, querying for \
firmware information, and burning a firmware image to a single Mellanox \
device.  These kernel modules are required to use MFT."

COMPATIBLE_HOST = "aarch64.*-linux"

inherit module

BV = "${@d.getVar('PV').split('-')[0]}"

SRC_URI = "file://kernel-mft-4.12.0-105.src.rpm"
SRC_URI[md5sum] = "155fca432321dc0329e24957c248525f"

S = "${WORKDIR}/kernel-mft-${BV}/"

SRC_URI += "file://Makefile.patch;patchdir=${S};striplevel=0"
SRC_URI += "file://COPYING"

LIC_FILES_CHKSUM = "file://${WORKDIR}/COPYING;md5=3e7b6871e251ce93d9b8cda88f5fba8f"

FILESEXTRAPATHS_prepend := "${MFT_PATH}:${OFED_SRC_PATH}/SRPMS:${TOPDIR}/mellanox/SRPMS:${TOPDIR}/distro/mlnx_ofed/MLNX_OFED_SRC-${MLNX_OFED_VERSION}/SRPMS:"

LICENSE = "GPLv2 | BSD"

EXTRA_OEMAKE += "KPVER=${KERNEL_VERSION} KSRC=${STAGING_KERNEL_DIR} CPU_ARCH=aarch64 SRC=${S}"

do_cve_check[depends] += "${PN}:do_prepare_recipe_sysroot"

# The source rpm file contains a tar file so we have extra work to do.

do_unpack_append() {
    bb.build.exec_func('do_extract_tarball', d)
}

do_extract_tarball() {
    cd ${WORKDIR}
    tar xvf kernel-mft-${BV}.tgz
}
