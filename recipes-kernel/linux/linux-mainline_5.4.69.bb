SUMMARY = "Linux mainline kernel"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"

inherit kernel fsl-kernel-localversion

S = "${WORKDIR}/git"

DEPENDS += "lzop-native bc-native"
RDEPENDS_${PN} += "wireless-regdb"

BRANCH = "linux-5.4.y"
LOCALVERSION = "+dr-1.5"
SRCREV = "a9518c1aec5b6a8e1a04bbd54e6ba9725ef0db4c"
SRC_URI = "git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux-stable.git;branch=${BRANCH} \
           file://defconfig \
           file://0001-spi-imx-initialize-cs-earlier.patch \
           "

COMPATIBLE_MACHINE = "(vec6200|vec6200-factory)"