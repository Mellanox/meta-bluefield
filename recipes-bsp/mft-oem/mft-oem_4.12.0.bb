SUMMARY = "Mellanox Firmware Tools"
DESCRIPTION = "The Mellanox Firmware Tools (MFT) package is a set of firmware \
management and debug tools for Mellanox devices. MFT can be used for \
generating a standard or customized Mellanox firmware image, querying for \
firmware information, and burning a firmware image to a single Mellanox \
device."

COMPATIBLE_HOST = "aarch64.*-linux"

S = "${WORKDIR}/mft-oem-${PV}/"

SRC_URI = "file://mft-oem-4.12.0-105.arm64.rpm;subdir=${S}"
SRC_URI[md5sum] = "745330acda0041baaef6dab6904569dd"

SRC_URI += "file://MFT_LICENSE.txt;subdir=${S}"

LICENSE = "CLOSED"
LIC_FILES_CHKSUM = "file://usr/share/doc/mft-oem-${PV}/COPYING;md5=acf1f43dc4568f15d675e391d5b73f7e"

FILESEXTRAPATHS_prepend := "${MFT_PATH}:${TOPDIR}/mellanox/RPMS:${TOPDIR}/distro/mlnx_ofed/MLNX_OFED_LINUX-${MLNX_OFED_VERSION}-${MLNX_OFED_BASE_OS}-aarch64/RPMS:"

# The RPM used for this recipe requires xz tools to unpack and
# the poky classes don't detect that dependency automatically.
do_unpack[depends] += "xz-native:do_populate_sysroot"

# Skip the unwanted steps
do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install () {
    # Do it carefully
    [ -d "${S}" ] || exit 1
    cd ${S} || exit 1
    tar --no-same-owner --exclude='.debug' --exclude='./patches' \
        -cpf - . \
        | tar --no-same-owner -xpf - -C ${D}
}

do_unpack_extra() {
    # Do it carefully
    [ -d "${S}" ] || exit 1
    cd ${S} || exit 1
    mkdir -p usr/share/doc/mft-oem-${PV}
    mv -f MFT_LICENSE.txt usr/share/doc/mft-oem-${PV}/COPYING
}

addtask unpack_extra after do_unpack before do_patch

RDEPENDS_${PN} = "bash krb5 usbutils python python-stringold python-logging python-subprocess"
INSANE_SKIP_${PN} = "ldflags already-stripped staticdev debug-files"

DIRFILES = "1"

PACKAGES = "${PN}"

FILES_${PN} = "/"
