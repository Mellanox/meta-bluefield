SUMMARY = "libnvdimm utility library"

DESCRIPTION = "Utility library for managing the libnvdimm \
(non-volatile memory device) sub-system in the Linux kernel. \
The LIBNVDIMM subsystem provides support for three types of \
NVDIMMs, namely,PMEM, BLK, and NVDIMM devices that can \
simultaneously support both PMEM and BLK mode access."

HOMEPAGE = "https://git.kernel.org/cgit/linux/kernel/git/nvdimm/nvdimm.git/tree/Documentation/nvdimm/nvdimm.txt?h=libnvdimm-for-next"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=e66651809cac5da60c8b80e9e4e79e08"

PV = "v57.1+git${SRCPV}"

inherit autotools-brokensep
inherit pkgconfig
inherit module-base

SRC_URI += "git://github.com/pmem/ndctl.git"
SRCREV = "df57f17c9f0141904865f6ff8b0257101b7d1104"

PKG_CONFIG_PATH =+ "${STAGING_LIBDIR_NATIVE}/pkgconfig"

S = "${WORKDIR}/git"
DEPENDS = "virtual/kernel kmod udev json-c"
EXTRA_OECONF += "--disable-docs"

do_configure_prepend() {
    ${S}/autogen.sh
}

FILES_${PN} += "/usr/share/bash-completion/completions/ndctl"

