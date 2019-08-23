#
# ex:ts=4:sw=4:sts=4:et
# -*- tab-width: 4; c-basic-offset: 4; indent-tabs-mode: nil -*-
#

SUMMARY = "ofed-scripts is a set of scripts providing information on the installed MLNX_OFED_LINUX packages"
SECTION = "ofed-scripts"
LICENSE = "GPLv2|BSD"

LIC_FILES_CHKSUM = "file://${WORKDIR}/COPYING;md5=3e7b6871e251ce93d9b8cda88f5fba8f"

# The RPM used for this recipe requires xz tools to unpack and
# the poky classes don't detect that dependency automatically.
do_unpack[depends] += "xz-native:do_populate_sysroot"

SRC_URI = "file://ofed-scripts-4.6-OFED.4.6.3.5.8.src.rpm"
SRC_URI[md5sum] = "e8e67c4c785fe5a2d85ac946bf2eb582"

SRC_URI += "file://COPYING;subdir=${WORKDIR}"

FILESEXTRAPATHS_prepend := "${OFED_SRC_PATH}/SRPMS:${TOPDIR}/mellanox/SRPMS:${MLNX_OFED_PATH}/MLNX_OFED_SRC-${MLNX_OFED_VERSION}/SRPMS:${TOPDIR}/distro/mlnx_ofed/MLNX_OFED_SRC-${MLNX_OFED_VERSION}/SRPMS:"

S = "${WORKDIR}/ofed-scripts-${PV}"

PACKAGES = "${PN}"

FILES_${PN} = "${bindir}/*"
FILES_${PN} += "${sbindir}/*"

# Skip the unwanted steps
do_configure[noexec] = "1"
do_compile[noexec] = "1"
do_package_qa[noexec] = "1"

INSANE_SKIP_${PN} += "already-stripped"

do_unpack_extra() {
    cd ${WORKDIR}
    tar xzf ${PN}-${PV}.tar.gz
}

# Install the files to ${D}
do_install () {
    # Do it carefully
    [ -d "${S}" ] || exit 1
    cd ${S} || exit 1
    mkdir -p ${D}/usr/bin
    mkdir -p ${D}/usr/sbin
    install -m 0755 sysinfo-snapshot.py ${D}/usr/sbin
    install -m 0755 uninstall.sh ${D}/usr/sbin/ofed_uninstall.sh
    install -m 0755 ofed_info ${D}/usr/bin
    install -m 0755 ofed_rpm_info ${D}/usr/bin
    install -m 0755 hca_self_test.ofed ${D}/usr/bin
    current_version=`grep -o '^OFED-internal-.*[0-9]' ${D}/usr/bin/ofed_info | head -1 | sed -e "s/OFED-internal-//g"`
    sed -i -e "s/^OFED-internal.*/MLNX_OFED_LINUX-${MLNX_OFED_VERSION} (OFED-$current_version):/g" ${D}/usr/bin/ofed_info
    sed -i -e "s/echo OFED-internal-${current_version}:/echo MLNX_OFED_LINUX-${MLNX_OFED_VERSION}:/g" ${D}/usr/bin/ofed_info
    sed -i -e "s/echo ${current_version}/echo ${MLNX_OFED_VERSION}/g" ${D}/usr/bin/ofed_info
    sed -i -e "s/OFED-internal/MLNX_OFED_LINUX/g" ${D}/usr/bin/ofed_info
    sed -i -e "s/${current_version}\.[[:digit:]]+/${MLNX_OFED_VERSION}/g" ${D}/usr/bin/ofed_info
}

addtask unpack_extra after do_unpack before do_patch
