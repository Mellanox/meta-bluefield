#
# ex:ts=4:sw=4:sts=4:et
# -*- tab-width: 4; c-basic-offset: 4; indent-tabs-mode: nil -*-
#

SUMMARY = "mlnx-iproute2"
SECTION = "mlnx-iproute2"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://${WORKDIR}/COPYING;md5=eb723b61539feef013de476e68b5c50a"

require mlnx-iproute2.inc

# The RPM used for this recipe requires xz tools to unpack and
# the poky classes don't detect that dependency automatically.
do_unpack[depends] += "xz-native:do_populate_sysroot"
do_cve_check[depends] += "${PN}:do_prepare_recipe_sysroot"

inherit pkgconfig

SRC_URI = "file://mlnx-iproute2-4.20.0-1.46358.src.rpm"
SRC_URI[md5sum] = "337401b3f966d3eb95ca85e2f99d43ba"
SRC_URI[sha256sum] = "a9f1db942fb9d34add7f87598f49051fb83960b0187ab3cc4ab383367d9ffbab"

SRC_URI += "file://configure-cross.patch"
 
# CFLAGS are computed in Makefile and reference CCOPTS
#
EXTRA_OEMAKE_append = " CCOPTS='${CFLAGS}'"

FILESEXTRAPATHS_prepend := "${OFED_SRC_PATH}/SRPMS:${TOPDIR}/mellanox/SRPMS:${MLNX_OFED_PATH}/MLNX_OFED_SRC-${MLNX_OFED_VERSION}/SRPMS:"

S = "${WORKDIR}/mlnx-iproute2-${PV}"
