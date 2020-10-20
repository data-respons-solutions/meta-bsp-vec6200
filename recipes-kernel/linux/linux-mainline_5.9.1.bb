SUMMARY = "Linux mainline kernel"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

inherit kernel fsl-kernel-localversion

S = "${WORKDIR}/git"

DEPENDS += "lzop-native bc-native"
RDEPENDS_${PN} += "wireless-regdb"

BRANCH = "linux-5.9.y"
LOCALVERSION = "+dr-1.5"
SRCREV = "213f323329f1567e09f85ddb54cfb80769340b50"
SRC_URI = "git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux-stable.git;branch=${BRANCH} \
           file://defconfig \
           "

COMPATIBLE_MACHINE = "(vec6200|vec6200-factory)"