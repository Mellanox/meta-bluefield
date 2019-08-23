FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

COMPATIBLE_HOST_aarch64 = "(aarch64).*-linux"

EXTRA_OEMAKE_append_aarch64 += " ARCH=aarch64"

SRC_URI_append_aarch64 = " file://Make-rules-bitbake.patch"
